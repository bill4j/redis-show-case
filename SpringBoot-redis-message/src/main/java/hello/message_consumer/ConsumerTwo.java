package hello.message_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Angelina
 */
@Service
public class ConsumerTwo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerTwo.class);

    public void consumeMessage(String message) {
        String strResult="----------------------------------message two: "+message+" have received!";
        LOGGER.info(strResult);
    }
}
