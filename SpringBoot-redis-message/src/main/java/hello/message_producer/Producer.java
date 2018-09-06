package hello.message_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Angelina
 */
@Service
public class Producer {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void sendMessage(String topic,String message){
        redisTemplate.convertAndSend(topic,message);
    }

}
