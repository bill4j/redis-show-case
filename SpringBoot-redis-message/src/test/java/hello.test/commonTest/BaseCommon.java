package hello.test.commonTest;

import hello.config.RedisConfig;
import hello.config.RedisProperties;
import hello.test.MainTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 常用指令
 * Created by lijun on 2018/10/9
 */
public class BaseCommon extends MainTest {

    @Autowired
    public RedisTemplate redisTemplate;

    @Test
    public void test() {
        System.out.println(RedisProperties.nameSpace);
        //System.out.println("test");
    }
}
