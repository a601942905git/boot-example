package com.boot.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

/**
 * com.boot.example.consumer.KafkaConsumerConfig
 *
 * @author lipeng
 * @date 2020/2/20 下午9:52
 */
@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> batchKafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        // 批量拉取消息内容
        factory.setBatchListener(true);
        // 设置消费者并发数，与分区数有关。如果有3个分区，并发数为3，那么每个container对应一个分区
        // 说明文档地址@link{https://stackoverflow.com/questions/50895344/spring-kafka-and-number-of-topic-consumers}
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        // factory.getContainerProperties().setCommitCallback();
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        // 默认情况下只会反序列化java.util和java.lang包下面的类，如果自定义示例类则无法序列化
        jsonDeserializer.addTrustedPackages("*");
        Map<String, Object> consumerProperties = this.kafkaProperties.buildConsumerProperties();
        // 关闭自动提交，开启收到提交
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 一个新的分组或者是offset被删除，从最早的offset开始消费
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(), jsonDeserializer);
    }
}
