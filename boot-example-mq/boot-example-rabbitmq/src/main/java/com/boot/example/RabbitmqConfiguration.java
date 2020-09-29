package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
        rabbitTemplate.setReturnCallback(new RabbitTemplateReturnCallback());
        return rabbitTemplate;
    }

    public static class RabbitTemplateConfirmCallback implements RabbitTemplate.ConfirmCallback {

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("call back confirm execute");
        }
    }

    public static class RabbitTemplateReturnCallback implements RabbitTemplate.ReturnCallback {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
                                    String routingKey) {

            log.info("call back return message execute");
        }
    }

}
