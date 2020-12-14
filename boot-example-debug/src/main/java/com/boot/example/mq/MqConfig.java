package com.boot.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.mq.MqConfig
 * 1.消息服务器针对可以路由的消息会返回basic.ack
 * 2.发布的消息被设置为mandatory(强制性)前提下，消息服务器针对不可以路由的消息会先返回basic.return，之后返回basic.ack，
 * 也就是会先回调returnedMessage()在回调confirm()
 *
 * @author lipeng
 * @date 2020/12/2 3:02 PM
 */
@Configuration
@Slf4j
public class MqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    /**
     * 消息服务器返回的basic.ack
     *
     * @param correlationData 关联数据对象
     * @param ack ack
     * @param cause 异常信息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("receive ack confirm：{} from broker server", ack);
    }

    /**
     * 消息服务器返回的basic.return
     *
     * @param message 消息对象
     * @param replyCode 响应code
     * @param replyText 响应文本
     * @param exchange 交换机
     * @param routingKey 路由key
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("receive return message：{} from broker server，reply code：{}，reply text：{}，" +
                "exchange：{}，routing key：{}", message.toString(), replyCode, replyText, exchange, routingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        configurer.configure(rabbitTemplate, connectionFactory);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
