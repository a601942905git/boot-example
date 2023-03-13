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

import java.util.HashMap;
import java.util.Map;

/**
 * com.boot.example.RabbitmqConfig
 *
 * @author lipeng
 * @date 2019/1/10 下午3:04
 */
@Configuration
@Slf4j
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback{

    public static final String EXCHANGE = "dead-letter-exchange";
    public static final String REDIRECT_ROUTING_KEY = "redirect-routingKey";
    public static final String REDIRECT_QUEUE_NAME = "redirect-queue";
    public static final String DLX_QUEUE_NAME = "dlx-queue";
    public static final String DLX_ROUTING_KEY = "dlx-routing-key";

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
        template.setReturnsCallback(this);
        return template;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue directQueue() {
        // 队列持久
        return new Queue(REDIRECT_QUEUE_NAME);
    }

    @Bean
    public Queue dlxQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", REDIRECT_ROUTING_KEY);
        return new Queue(DLX_QUEUE_NAME, true, false, false, params);
    }

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(directQueue()).to(exchange()).with(RabbitmqConfig.REDIRECT_ROUTING_KEY);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(exchange()).with(RabbitmqConfig.DLX_ROUTING_KEY);
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
            System.out.println("【消息】：" + correlationData.getId() + "，发送成功");
        } else {
            System.out.println("【消息】：" + correlationData.getId() + "，发送失败");
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("receive return message：{} from broker server，reply code：{}，reply text：{}，" +
                        "exchange：{}，routing key：{}", returnedMessage.getMessage().toString(), returnedMessage.getReplyCode(),
                returnedMessage.getReplyText(), returnedMessage.getExchange(), returnedMessage.getRoutingKey());
    }
}
