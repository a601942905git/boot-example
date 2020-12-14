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

    /**
     * 发送普通消息
     */
    public void sendMessage() {
        rabbitTemplate.convertAndSend("test", "test", "mq produce send a message");
    }

    /**
     * 发送不可路由的消息，消息服务器会先返回basic.return，再返回basic.ack
     */
    public void sendUnRoutableMessage() {
        rabbitTemplate.convertAndSend("test", "test4", "mq produce send a message");
    }

    /**
     * 使用不存在的交换机发送消息
     */
    public void sendMessageWithUnKnowExchange() {
        rabbitTemplate.convertAndSend("test1", "test1", "mq produce send a message");
    }

    /**
     * 发送复杂消息
     */
    public void sendComplexMessage() {
        Order order = new Order();
        order.setOrderId(10001);
        order.setOrderNo("20201213131000");
        rabbitTemplate.convertAndSend("test", "test3", order);
    }
}
