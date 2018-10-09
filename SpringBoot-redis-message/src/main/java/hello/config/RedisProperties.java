package hello.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring-data-redis 实现了默认的配置注入，对应的注入bean为 org.springframework.boot.autoconfigure.data.redis
 * Created by lijun on 2018/10/9
 */
@ConfigurationProperties(prefix = "spring.redis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisProperties {
    private String host = "localhost";
    private String password;
    private int port = 6379;
}
