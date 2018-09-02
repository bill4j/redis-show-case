package hello.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis操作简单封装
 *
 * @author Angelina
 */
@Service("redisOperations")
public class RedisOperations {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 向redis中设置值,并指定过期时间.
     *
     * @param key     key
     * @param value   value
     * @param seconds 过期时间
     */
    public void set(String key, String value, Long seconds) {
        redisTemplate.boundValueOps(key).set(value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取redis中指定key的值.
     *
     * @param key key
     * @return 返回值
     */
    public String get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }
}
