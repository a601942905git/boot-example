package com.boot.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * com.boot.example.MqSenderTest
 *
 * @author lipeng
 * @date 2020/9/27 4:11 PM
 */
@SpringBootTest(classes = RabbitmqApplication.class)
public class MqSenderTest {

    @Autowired
    private MqSender mqSender;

    @Test
    public void send() {
        mqSender.send("hello rabbitmq");
    }
}
