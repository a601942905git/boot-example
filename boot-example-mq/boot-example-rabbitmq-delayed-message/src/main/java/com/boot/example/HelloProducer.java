package com.boot.example;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * com.boot.example.HelloProducer
 *
 * @author lipeng
 * @date 2019-01-13 21:37
 */
@Component
public class HelloProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        LocalDateTime localDateTime = LocalDateTime.now();
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_KEY, "hello rabbitMQ，发送时间：" + localDateTime,
                message -> {
                    message.getMessageProperties().setHeader("x-delay", 3000);
                    return message;
                },
                correlationData);
    }
}
