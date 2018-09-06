package hello.test;

import hello.Application;
import hello.message_producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class MessageTest {

    @Autowired
    private Producer producer;

    @Test
    public void hahaTest(){
        producer.sendMessage("AngelinaOne","this is a message one from channelOne");
        producer.sendMessage("AngelinaTwo","this is a message one from channelTwo");
    }

}
