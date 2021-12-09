package com.boot.example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.RabbitMqController
 *
 * @author lipeng
 * @date 2021/12/8 7:13 PM
 */
@RestController
public class RabbitMqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendBusinessMessage")
    public void sendBusinessMessage() throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitmqConfig.BUSINESS_EXCHANGE, RabbitmqConfig.BUSINESS_ROUTING_KEY, "send message");
        TimeUnit.SECONDS.sleep(10000);
    }
}
