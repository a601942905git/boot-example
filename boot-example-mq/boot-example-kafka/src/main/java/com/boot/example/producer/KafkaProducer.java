package com.boot.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * com.boot.example.producer.KafkaProducer
 *
 * @author lipeng
 * @date 2020/2/20 下午9:15
 */
@Component
@Slf4j
public class KafkaProducer<K, V> {

    @Autowired
    private KafkaTemplate<K, V> kafkaTemplate;

    public void send(String topic, V value) {
        ListenableFuture<SendResult<K, V>> listenableFuture = kafkaTemplate.send(topic, value);
        listenableFuture.addCallback(result -> {
            log.info("send result：" + result);
        }, ex -> {
            log.info("send exception：" + ex);
        });
    }
}
