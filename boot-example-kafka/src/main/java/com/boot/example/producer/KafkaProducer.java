package com.boot.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.producer.KafkaProducer
 *
 * @author lipeng
 * @date 2020/2/20 下午9:15
 */
@Component
public class KafkaProducer<K, V> {

    @Autowired
    private KafkaTemplate<K, V> kafkaTemplate;

    public void send(String topic, V value) {
        kafkaTemplate.send(topic, value);
    }
}
