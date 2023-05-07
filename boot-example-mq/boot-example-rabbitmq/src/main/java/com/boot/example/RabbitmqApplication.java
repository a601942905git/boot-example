package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.RabbitmqApplication
 * 框架自动提交：
 * @see org.springframework.amqp.rabbit.listener.BlockingQueueConsumer#commitIfNecessary(boolean)
 * 生产者confirm：
 * @see org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType
 * SIMPLE:需要调用org.springframework.amqp.rabbit.core.RabbitTemplate#waitForConfirms(long)方法同步等待结果
 * CORRELATED:需要给RabbitTemplate设置setConfirmCallback，异步等待结果
 *
 * @author lipeng
 * @date 2020/9/27 3:30 PM
 */
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
