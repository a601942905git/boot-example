package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.RabbitmqApplication
 *
 * 框架自动提交：
 *  @see org.springframework.amqp.rabbit.listener.BlockingQueueConsumer#commitIfNecessary(boolean)
 *
 * @author lipeng
 * @date 2020/9/27 3:30 PM
 */
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
