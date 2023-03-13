package com.boot.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

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
        ListenableFuture<SendResult<K, V>> listenableFuture = kafkaTemplate.send(topic, value);
        listenableFuture.addCallback(
                result -> log.info("send message success，result：" + result),
                ex -> log.info("send message exception：" + ex));
    }
}
