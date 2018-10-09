package hello.test;

import hello.message_producer.Producer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageTest extends MainTest{

    @Autowired
    private Producer producer;

    @Test
    public void hahaTest(){
        producer.sendMessage("AngelinaOne","this is a message one from channelOne");
        producer.sendMessage("AngelinaTwo","this is a message one from channelTwo");
    }

}
