package com.boot.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.boot.example.consumer.KafkaConsumer
 *
 * @author lipeng
 * @date 2020/2/20 下午9:21
 */
@Component
@Slf4j
public class KafkaMessageConsumer {

    /**
     * 1.当传入Acknowledgment参数是，需要指定监听器的模式为手动模式
     * @see ContainerProperties.AckMode
     *
     * 2.发送消息：
     * aa
     * bb
     * test
     * cc
     * dd
     * ee
     * 3.消费端输出：
     * receive message topic：product, partition：0，offset：5，message：aa
     * receive message topic：product, partition：0，offset：6，message：bb
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：7，message：test
     * receive message topic：product, partition：0，offset：8，message：cc
     * receive message topic：product, partition：0，offset：9，message：dd
     * receive message topic：product, partition：0，offset：10，message：ee
     * 4.从输出结果可以看出，如果消费异常，会对异常消息进行重试，达到最大重试次数9次后，会将消息丢弃，继续消费后面的消息
     * 重试逻辑参考：
     * @see KafkaMessageListenerContainer#doStart() 创建ListenerConsumer
     * @see KafkaMessageListenerContainer.ListenerConsumer#determineErrorHandler(GenericErrorHandler),
     * @see SeekToCurrentErrorHandler#SeekToCurrentErrorHandler()
     * @see SeekToCurrentErrorHandler#handle(Exception, List, Consumer, MessageListenerContainer)
     *
     * @param consumerRecord 消息记录
     * @param ack ack对象
     */
    @KafkaListener(topics = "product", groupId = "product1")
    public void consumeMessage1(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {
        log.info("receive message topic：{}, partition：{}，offset：{}，message：{}", consumerRecord.topic(),
                consumerRecord.partition(), consumerRecord.offset(), consumerRecord.value());
        if ("test".equals(consumerRecord.value())) {
            throw new IllegalArgumentException("message content is empty");
        }
        ack.acknowledge();
    }

    /**
     * 发送消息：
     * aa
     * bb
     * test
     * cc
     * dd
     * 1.消费端输出：
     * receive message topic：product, partition：0，offset：27，message：aa
     * receive message topic：product, partition：0，offset：28，message：bb
     * receive message topic：product, partition：0，offset：29，message：test
     * receive message topic：product, partition：0，offset：30，message：cc
     * receive message topic：product, partition：0，offset：31，message：dd
     * 2.现象：
     * 第三条消息丢失，通过bin/kafka-consumer-groups.sh --group product2 --bootstrap-server localhost:9092 --describe命令查看消费组信息，当前偏移量为32
     * 3.ack.acknowledge()逻辑：
     * 会把topic-partition-offset存入map中，如果一次拉取多条消息，map中存储最新offset，在下一次拉取消息之前进行提交
     *
     *
     * @param consumerRecord 消息记录
     * @param ack ack对象
     */
    @KafkaListener(topics = "product", groupId = "product2")
    public void consumeMessage2(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {
        log.info("receive message topic：{}, partition：{}，offset：{}，message：{}", consumerRecord.topic(),
                consumerRecord.partition(), consumerRecord.offset(), consumerRecord.value());
        try {
            if ("test".equals(consumerRecord.value())) {
                throw new IllegalArgumentException("message content is empty");
            }
            ack.acknowledge();
        } catch (Exception e) {
            log.error("consume message error：", e);
        }
    }
}
