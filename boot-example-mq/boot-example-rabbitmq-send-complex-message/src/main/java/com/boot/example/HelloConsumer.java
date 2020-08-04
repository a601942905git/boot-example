package com.boot.example;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    public void helloConsumer(User user, Channel channel, Message message) throws IOException {
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Object corRelationId = message.getMessageProperties().getHeaders().get(PublisherCallbackChannelImpl.RETURNED_MESSAGE_CORRELATION_KEY);
        try {
            System.out.println("【receive message】：" + user + "，【corRelationId】：" + corRelationId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 当出现异常的时候，拒绝消息，并将消息放入队列中，这样可以进行再次消费
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
