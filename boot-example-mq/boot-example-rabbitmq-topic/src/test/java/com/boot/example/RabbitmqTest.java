package com.boot.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * com.boot.example.RabbitmqTest
 *
 * @author lipeng
 * @date 2019/1/10 下午3:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqTest {

    @Autowired
    private HelloProducer helloProducer;

    @Test
    public void sendMessage() throws InterruptedException, IOException {
        helloProducer.sendMsg();
    }
}
