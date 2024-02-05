package com.boot.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * com.boot.example.producer.KafkaSender
 *
 * @author lipeng
 * @date 2021/3/2 9:13 PM
 */
@Component
@Slf4j
public class KafkaMessageSender<K, V> {

    @Autowired
    private KafkaTemplate<K, V> kafkaTemplate;

    public void sendSimpleMessage(String topic, V value) {
        CompletableFuture<SendResult<K, V>> completableFuture = kafkaTemplate.send(topic, value);
        // 异步发送消息，当发送完成后，会回调whenComplete方法
        completableFuture.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                log.error("发送消息失败，topic：{}，value：{}", topic, value, throwable);
            } else {
                log.info("发送消息成功，topic：{}，value：{}", topic, value);
            }
        });
    }
}
