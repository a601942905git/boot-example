package com.boot.example;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_KEY, "hello rabbitMQ with exchange", correlationData);

        // 不指定exchange，使用默认的exchange，默认exchange和所有的队列进行绑定，绑定的key就是队列名称
        rabbitTemplate.convertAndSend(null,
                RabbitmqConfig.QUEUE_NAME, "hello rabbitMQ with no exchange", correlationData);

    }
}
