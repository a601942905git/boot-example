package com.boot.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.RabbitmqTest
 *
 * @author lipeng
 * @date 2019/1/10 下午3:12
 */
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqTest {

    @Autowired
    private HelloProducer helloProducer;

    @Test
    public void sendMessage() throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        helloProducer.sendMsg();
    }
}
