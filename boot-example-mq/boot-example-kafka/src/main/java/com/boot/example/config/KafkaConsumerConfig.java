package com.boot.example.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

/**
 * com.boot.example.config.KafkaConsumerConfig
 *
 * @author lipeng
 * @date 2021/3/4 8:05 PM
 */
@Configuration
public class KafkaConsumerConfig {

    @Bean
    @ConditionalOnMissingBean(ConsumerFactory.class)
    public ConsumerFactory<?, ?> kafkaConsumerFactory(KafkaProperties kafkaProperties) {
        // 手动ack，由使用者控制偏移量的提交
        kafkaProperties.getListener().setAckMode(ContainerProperties.AckMode.MANUAL);
        Map<String, Object> consumerProperties = kafkaProperties.buildProducerProperties();
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 设置value反序列化方式，采用何种方式将字节数组转换为目标对象
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        // 参考：JsonDeserializer
        // 用于解决该异常： The class 'com.boot.example.entity.Student' is not in the trusted packages: [java.util, java.lang]. If you believe this class is safe to deserialize, please provide its name. If the serialization is only done by a trusted source, you can also enable trust all (*).
        consumerProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.boot.example.*");
        // 会话超时时间，如果消费者在该时间之前没有向broker发送心跳，broker会将消费者从消费者组中移除并触发重新平衡
        consumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        // 最大拉取间隔周期，超过该时间没有拉取数据，消费者会主动离开消费者组，另一个消费者会接管对应的partition
        consumerProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        // 一次拉取的最大记录数
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        // 关闭自动提交，enable.auto.commit:true意味着会自动提交偏移量，依赖于auto.commit.interval.ms设置的评率
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }
}
