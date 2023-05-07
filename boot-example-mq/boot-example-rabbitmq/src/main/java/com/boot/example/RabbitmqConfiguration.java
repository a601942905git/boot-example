package com.boot.example;

import com.rabbitmq.client.Command;
import com.rabbitmq.client.impl.ChannelN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.RabbitmqConfiguration
 *
 * @author lipeng
 * @date 2020/9/27 4:35 PM
 */
@Configuration
@Slf4j
public class RabbitmqConfiguration {

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer rabbitTemplateConfigurer,
                                         ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplateConfigurer.configure(rabbitTemplate, connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplateConfirmCallback());
        rabbitTemplate.setReturnsCallback(new RabbitTemplateReturnCallback());
        return rabbitTemplate;
    }

    /**
     * @see ChannelN#processAsync(Command)
     * @see com.rabbitmq.client.impl.ChannelN#callConfirmListeners(com.rabbitmq.client.Command, com.rabbitmq.client.impl.AMQImpl.Basic.Ack)
     * @see PublisherCallbackChannelImpl#handleAck(long, boolean)
     *
     */
    public static class RabbitTemplateConfirmCallback implements RabbitTemplate.ConfirmCallback {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("call back confirm execute，correlation data id：{}", correlationData.getId());
        }
    }

    public static class RabbitTemplateReturnCallback implements RabbitTemplate.ReturnsCallback {

        @Override
        public void returnedMessage(ReturnedMessage returnedMessage) {
            log.info("reply code：{}，reply text：{}", returnedMessage.getReplyCode(), returnedMessage.getReplyText());
        }
    }

}
