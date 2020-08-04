package com.boot.example;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelImpl;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.HelloConsumer
 *
 * @author lipeng
 * @date 2019-01-13 15:24
 */
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String content, Message message) {
        Object corRelationId = message.getMessageProperties().getHeaders().get(PublisherCallbackChannelImpl.RETURNED_MESSAGE_CORRELATION_KEY);
        System.out.println("【consumer1 boot-example-fanout-queue receive message】：" + content + "，【corRelationId】：" + corRelationId);
    }
}
