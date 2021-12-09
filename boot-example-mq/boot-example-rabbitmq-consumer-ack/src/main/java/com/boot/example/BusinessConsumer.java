package com.boot.example;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.HelloConsumer
 *
 * @author lipeng
 * @date 2019-01-13 15:24
 */
@Component
@RabbitListener(queues = RabbitmqConfig.BUSINESS_QUEUE_NAME)
@Slf4j
public class BusinessConsumer {

    /**
     * 操作场景：
     * 1.通过RabbitmqApplication启动类启动应用程序
     * 2.调用/sendBusinessMessage接口发送消息
     * 3.RabbitMQ broker将消息发送给消费者
     * 4.消费者收到消息后进行消费
     * 5.消费者消费消息过程中，应用程序关闭，断开channel，断开connection，未ack的消息会被重新放入broker中
     *
     * @param content 消息内容
     * @param channel channel通道
     * @param message message对象
     */
    @RabbitHandler
    public void helloConsumer(String content, Channel channel, Message message) {
        log.info("business consumer receive message：{}", content);
        try {
            // 模拟业务执行耗时
            TimeUnit.SECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
