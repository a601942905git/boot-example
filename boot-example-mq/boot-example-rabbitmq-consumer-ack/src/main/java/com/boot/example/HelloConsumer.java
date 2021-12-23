package com.boot.example;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.boot.example.HelloConsumer
 * @RabbitListener 注解中的concurrency属性用来设置消费者并发数，如果是纯数字，那么就是固定并发数；如果非纯数字，那么就是动态并发数
 * 比如设置1-5，在负载较低的情况下，就只有1个消费者；在负载较高的情况下，消费者最多可达5个
 *
 * @author lipeng
 * @date 2019-01-13 15:24
 */
@Component
public class HelloConsumer {

    @RabbitListener(queues = RabbitmqConfig.QUEUE_NAME, concurrency = "1-5")
    public void helloConsumer(String content, Channel channel, Message message) throws IOException {
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("【receive message】：" + content);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 当出现异常的时候，拒绝消息，并将消息放入队列中，这样可以进行再次消费
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
