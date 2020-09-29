package com.boot.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * com.boot.example.MqSenderTest
 *
 * @author lipeng
 * @date 2020/9/27 4:11 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class MqSenderTest {

    @Autowired
    private MqSender mqSender;

    @Test
    public void send() {
        mqSender.send("hello rabbitmq");
    }
}
