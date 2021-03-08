package com.boot.example.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.unit.DataSize;

import java.util.Map;

/**
 * com.boot.example.config.KafkaConfig
 *
 * @author lipeng
 * @date 2021/3/4 7:44 PM
 */
@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<Object, Object> kafkaProducerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();
        // 设置生产者key序列化方式，具体可参考org.apache.kafka.common.serialization.Serializer的实现
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 设置生产者value序列化方式，具体可参考org.apache.kafka.common.serialization.Serializer的实现
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // 请求被认为完成前生产者要求leader应收到指定数量的ack，默认值1
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        // 缓冲池大小，当消息发送速度超过发送给broker的速度，会导致缓冲池耗尽并对生产者进行max.block.ms时间阻塞，超时后会抛出异常，默认值32M
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, DataSize.ofMegabytes(32).toBytes());
        // 设置压缩算法，默认值none
        producerProperties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        // 重试次数，如果max.in.flight.requests.per.connection不设置为1，会导致发送消息错乱，默认值2147483647
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, "3");
        // 消息延迟发送时间，当消息大小超过batch.size后，该配置被忽略，默认为0
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

}
