package com.boot.example.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Collections;
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
    public ConsumerFactory<Object, Object> kafkaConsumerFactory(KafkaProperties kafkaProperties) {
        // 手动ack，由使用者控制偏移量的提交
        kafkaProperties.getListener().setAckMode(ContainerProperties.AckMode.MANUAL);
        Map<String, Object> consumerProperties = kafkaProperties.buildProducerProperties();
        // 设置key反序列化方式
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 设置value反序列化方式，采用何种方式将字节数组转换为目标对象
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        // 针对请求服务器端返回的最小数据，默认值为1
        consumerProperties.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
        // 服务器返回记录最大大小，默认值1M
        consumerProperties.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1048576);
        // 消费者与服务器之间心跳时间，默认值为3000(3s)
        consumerProperties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        // 会话超时时间，如果消费者在该时间之前没有向broker发送心跳，broker会将消费者从消费者组中移除并触发重新平衡，默认值为10000(10s)
        consumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        // 参考：JsonDeserializer
        // 用于解决该异常： The class 'com.boot.example.entity.Student' is not in the trusted packages: [java.util, java.lang]. If you believe this class is safe to deserialize, please provide its name. If the serialization is only done by a trusted source, you can also enable trust all (*).
        consumerProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.boot.example.*");
        // 最大拉取间隔周期，超过该时间没有拉取数据，消费者会主动离开消费者组，另一个消费者会接管对应的partition
        consumerProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        // 一次拉取的最大记录数
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        // 关闭自动提交，enable.auto.commit:true意味着会自动提交偏移量，依赖于auto.commit.interval.ms设置的评率
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 设置消费者分区策略：范围、轮询、粘性
        consumerProperties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,
                Collections.singletonList(CooperativeStickyAssignor.class));
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Object, Object> batchKafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        // 设置批处理标识
        factory.setBatchListener(true);
        return factory;
    }
}
