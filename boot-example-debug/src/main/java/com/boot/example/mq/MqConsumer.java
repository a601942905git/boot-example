package com.boot.example.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.boot.example.mq.MqConsumer
 * 1.扫描含有@RabbitListener注解的Bean
 * 2.创建MethodRabbitListenerEndpoint
 * 3.从容器中获取SimpleRabbitListenerContainerFactory
 * 4.创建SimpleMessageListenerContainer
 * 5.创建BlockingQueueConsumer
 * 6.创建AsyncMessageProcessingConsumer
 * 7.创建ChannelN并绑定InternalConsumer
 * 8.当broker队列中有消息是会推送给指定的Channel，InternalConsumer会将消息放入BlockingQueueConsumer队列中
 * 9.无限循环从BlockingQueueConsumer队列中获取消息进行消费
 * 10.如果自动ack，则会对@RabbitListener标注的方法进行try，最终进行ack
 *
 * @author lipeng
 * @date 2020/11/23 3:09 PM
 */
@Component
@Slf4j
public class MqConsumer {

    /**
     * 消费者方法参数位置是任意的
     * @param message
     * @param channel
     * @param content
     */
    @RabbitListener(id = "consumerMessage1", queues = "test1", ackMode = "MANUAL")
    public void consumeMessage1(Message message, Channel channel, String content) {
        log.info("receive message1：{}", content);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("channel basic ack exception：", e);
        }
    }

    @RabbitListener(id = "consumerMessage2", queues = "test2")
    public void consumeMessage2(String message) {
        log.info("receive message2：{}", message);
    }
}
