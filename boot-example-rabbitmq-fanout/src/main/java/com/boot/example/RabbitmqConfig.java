package com.boot.example;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * com.boot.example.RabbitmqConfig
 *
 * @author lipeng
 * @date 2019/1/10 下午3:04
 */
@Configuration
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    public static final String EXCHANGE   = "spring-boot-fanout-exchange";
    public static final String QUEUE_NAME = "boot-example-fanout-queue";
    public static final String QUEUE_NAME1 = "boot-example-fanout-queue1";

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
        connectionFactory.setPublisherConfirms(true);
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
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE);
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

    @Bean
    public Queue queue1() {
        // 队列持久
        return new Queue(QUEUE_NAME1);
    }

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
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
            System.out.println("【消息】：" + correlationData.getId() + "，发送成功");
        } else {
            System.out.println("【消息】：" + correlationData.getId() + "，发送失败");
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("【message】：" + message);
        System.out.println("【replyCode】：" + replyCode);
        System.out.println("【replyText】：" + replyText);
        System.out.println("【exchange】：" + exchange);
        System.out.println("【routingKey】：" + routingKey);
    }
}
