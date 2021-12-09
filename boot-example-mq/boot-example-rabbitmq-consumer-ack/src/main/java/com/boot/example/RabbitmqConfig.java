package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

/**
 * com.boot.example.RabbitmqConfig
 *
 * @author lipeng
 * @date 2019/1/10 下午3:04
 */
@Configuration
@Slf4j
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    public static final String EXCHANGE   = "spring-boot-exchange";
    public static final String ROUTING_KEY = "spring-boot-routingKey";
    public static final String QUEUE_NAME = "boot-example-queue";

    public static final String BUSINESS_EXCHANGE   = "business-exchange";
    public static final String BUSINESS_ROUTING_KEY = "business-routingKey";
    public static final String BUSINESS_QUEUE_NAME = "business-queue";

    /**
     * 创建连接工厂
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("boot-example");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    /**
     * 创建RabbitTemplate
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMandatory(true);
        template.setConfirmCallback(this);
        template.setReturnCallback(this);
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     *
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue queue() {
        // 队列持久
        return new Queue(QUEUE_NAME);
    }

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabbitmqConfig.ROUTING_KEY);
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        /**
         * 默认为自动确认模式，此处改成手动，这样我们可以在逻辑代码执行完成的情况下进行手动确认
         * 保证消息一定被消费掉
         */
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 当消息发送到Broker的Exchange中，该方法被调用
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("【消息】：{} 发送成功", Objects.nonNull(correlationData) ? correlationData.getId() : "");
        } else {
            log.info("【消息】：{} 发送失败", Objects.nonNull(correlationData) ? correlationData.getId() : "");
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("【message】：{}", message);
        log.info("【replyCode】：{}", replyCode);
        log.info("【replyText】：{}", replyText);
        log.info("【exchange】：{}", exchange);
        log.info("【routingKey】：{}", routingKey);
    }
}
