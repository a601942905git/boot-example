package com.boot.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

/**
 * com.boot.example.producer.KafkaProducerConfig
 *
 * @author lipeng
 * @date 2020/2/20 下午9:42
 */
@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate(ProducerListener<Object, Object> kafkaProducerListener,
                                             ProducerFactory<Object, Object> producerFactory) {
        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(kafkaProducerListener);
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<Object, Object> buildKafkaProducerFactory() {
        Map<String, Object> producerProperties = this.kafkaProperties.buildProducerProperties();
        // producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    public NewTopic firstTopic() {
        return new NewTopic("first", 3, (short) 3);
    }

    @Bean
    public NewTopic secondTopic() {
        return new NewTopic("second", 3, (short) 3);
    }

    @Bean
    public NewTopic thirdTopic() {
        return new NewTopic("third", 3, (short) 3);
    }

    @Bean
    public NewTopic myTopic() {
        return new NewTopic("myTopic", 3, (short) 3);
    }

    @Bean
    public NewTopic batchTopic() {
        return new NewTopic("batch", 3, (short) 3);
    }
}
