package com.boot.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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
        kafkaTemplate.send(topic, value);
    }
}
