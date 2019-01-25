package com.boot.example;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.HelloConsumer
 *
 * @author lipeng
 * @date 2019-01-13 15:24
 */
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer1 {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【consumer2 receive message】：" + message);
    }
}
