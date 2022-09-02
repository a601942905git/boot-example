package com.boot.example;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.MqSender
 * mq消息发送者
 *
 * @author lipeng
 * @date 2020/9/27 4:07 PM
 */
@Component
public class MqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object message) {
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend("test", "", message, correlationData);
    }
}
