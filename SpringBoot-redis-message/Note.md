# spring-data-redis

## 事务篇

### Redis事务基础
1) Redis 事务分为三个阶段：开始一个事务，命令入队，批量执行命令。
2) Redis 事务实现基于乐观锁的概念。
3) Redis 事务是批量执行命令，如果在批量指令中遇到错误，不会回滚已执行的脚本，也不会中断后续脚本的执行，因此不具有原子性。
4) Redis 事务满足 隔离性、一致性，不满足 原子性、持久性。

### Redis 支持事务的指令
+ `WATCH key [key ...]` : 监视一个或者多个`key`,如果在执行前这些`key`被其他的命令改动，那么事务将被打断。
+ `UNWATCH key [key ...]` : 取消监视一个或多个`key`,如果在该命令之前执行了`EXEC`和`DISCARD`,就没必须要执行`UNWATCH`
+ `EXEC` : 执行所有事务块内的命令，假如某个正在处于`WATCH`监视下的`key`有相关的命令，那么`EXEC`在`KEY`没有被其他命令改动的情况下执行生效，否则，命令会被打断，所有的命令都不会生效。
+ `MULTI` : 标记一个事务块开始，事务块的内的多条命令会按照先后顺序被放进一个队列中执行，最后由`EXEC`命令原子执行。
+ `DISCARD` : 取消事务执行，如果当前使用`WATCH`，相当于`UNWATCH`。

### spring-data-redis 批处理支持
如果我们在需要批量执行脚本操作，如：在`set`中插入十条数据，普通操作需要十次连接，`spring-data-redis`提供了一次传输命令并且批量接受每个指令的返回结果，示例代码(来自官网)：
```
List<Object> results = stringRedisTemplate.executePipelined(
  new RedisCallback<Object>() {
    public Object doInRedis(RedisConnection connection) throws DataAccessException {
      StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
      for(int i=0; i< batchSize; i++) {
        stringRedisConn.rPop("myqueue");
      }
    return null;
  }
});
```
**该方案为批量传输命令以此来减少连接提高效率，并非`redis`事物支持**


### spring-data-redis 事务实现示例
**在集群模式下，`spring-data-redis`不支持事物实现**

示例代码(org.springframework.data.redis.support.collections.DefaultRedisMap):
```
redisTemplate.getOperations().execute(new SessionCallback<Boolean>() {
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean execute(RedisOperations ops) {
		for (;;) {
			ops.watch(Collections.singleton(getKey()));
			V v = get(key);
			if (value.equals(v)) {
				ops.multi();
				remove(key);
				if (ops.exec(ops.getHashValueSerializer()) != null) {
					return true;
				}
			} else {
				return false;
			}
		}
	}
});
```
#### 实现方式:
1) 使用`watch(key)`开启一个`key`监控。
2) `multi`开启事物模块。
3) `exec`执行脚本命令。根据`redis`指令描述，此时也有`unwatch(key)`功能。

##### 代码分析：

此处采用自旋的方式进行阻塞与不断的尝试，当处理`remove`操作时发现，`key`对应的`value`被修改，则命令不生效，再次进行修改尝试，直至修改成功。

##### 采坑记录
1. 由于事物具有隔离性，因此在`multi`开启的事物块中，是无法获取到`watch(key)`对应的`key`值，此处的代码在事物块之前获取了该值。同时此处使用了`watch(Collections.singleton(getKey()))`代替`watch(key())`避免在事物块中无法获取该值。
2. `exe()`会实现`unwatch`功能，所以每次循环结束的时已经关闭了`key`的监控，需要重新开启。
3. 采用该方式需要在`redisTemplate`中开启事物支持` template.setEnableTransactionSupport(true)`

### spring-data-redis 脚本实现示例
***使用`lua`脚本也可以实现原子操作，且没有环境限制***

示例代码：
```
 RedisScript<Object> redisScript = RedisScript.of(
        "local current = redis.call('GET', KEYS[1])\n" +
        "if current == ARGV[1]\n" +
        "  then redis.call('SET', KEYS[1], ARGV[2])\n" +
        "  return true\n" +
        "end\n" +
        "return false");
DefaultRedisScript defaultRedisScript = new DefaultRedisScript(redisScript.getScriptAsString(), Boolean.class);
Object key = redisTemplate.execute(defaultRedisScript, keys);
```
##### 采坑记录
1. 由于执行脚本需要生成一个`SHA1`校验码，因此对于脚本生成，不建议在每次使用时生成，根据官网建议
`It is ideal to configure a single instance of DefaultRedisScript in your application context to avoid re-calculation of the script’s SHA1 on every script execution.`



### 其他
1. `spring-data-redis` 中不仅仅提供了基本命令操作，在`org.springframework.data.redis.support`中提供了基于`redis`的集合和原子计数器等实现。
2. 在使用`spring-data-redis`中一般会修改其默认的序列化实现方案，在`org.springframework.data.redis.serializer`中有官方提供的序列化方案选择。

