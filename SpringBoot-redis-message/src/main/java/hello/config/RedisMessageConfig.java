package hello.config;

import hello.message_consumer.ConsumerOne;
import hello.message_consumer.ConsumerTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author Angelina
 */
@Configuration
public class RedisMessageConfig {

    private static final String MESSAGE_TOPIC_NAME_ONE = "AngelinaOne";
    private static final String MESSAGE_TOPIC_NAME_TWO = "AngelinaTwo";

    private static final String MESSAGE_CONSUMER_METHOD = "consumeMessage";

    @Autowired
    private ConsumerOne consumerOne;

    @Autowired
    private ConsumerTwo consumerTwo;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapterOne,
                                            MessageListenerAdapter listenerAdapterTwo) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapterOne, new PatternTopic(MESSAGE_TOPIC_NAME_ONE));
        container.addMessageListener(listenerAdapterTwo, new PatternTopic(MESSAGE_TOPIC_NAME_TWO));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterOne() {
        return new MessageListenerAdapter(consumerOne,MESSAGE_CONSUMER_METHOD);
    }

    @Bean
    MessageListenerAdapter listenerAdapterTwo() {
        return new MessageListenerAdapter(consumerTwo, MESSAGE_CONSUMER_METHOD);
    }
}
