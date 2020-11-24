package com.boot.example.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.mq.MqSender
 *
 * @author lipeng
 * @date 2020/11/23 3:09 PM
 */
@Component
public class MqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage() {
        rabbitTemplate.convertAndSend("test", "test", "mq produce send a message");
    }
}
