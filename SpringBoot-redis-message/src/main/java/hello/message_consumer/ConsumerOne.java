package hello.message_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Angelina
 */
@Service()
public class ConsumerOne {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerOne.class);

    public void consumeMessage(String message) {
        String strResult = "----------------------------------message one: " + message + " have received!";
        LOGGER.info(strResult);
    }

}
