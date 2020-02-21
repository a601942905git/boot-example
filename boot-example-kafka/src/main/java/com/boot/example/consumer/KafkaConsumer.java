package com.boot.example.consumer;

import com.boot.example.entity.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.consumer.KafkaConsumer
 *
 * @author lipeng
 * @date 2020/2/20 下午9:21
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "first", groupId = "consumer_first")
    public void consumeStudent(String content) {
        System.out.println("receive content：" + content);
    }

    @KafkaListener(topics = "second", groupId = "consumer_student")
    public void consumeStudent(Student student) {
        System.out.println("receive student：" + student);
    }

    @KafkaListener(topics = "third", groupId = "consumer_third")
    public void consumeRecord(ConsumerRecord<?, ?> consumerRecord) {
        System.out.println("receive record：" + consumerRecord);
    }

    /**
     * 批量消息记录
     *
     * @param consumerRecords 消费者记录信息
     */
    @KafkaListener(topics = "batch", groupId = "consumer_batch", containerFactory = "batchKafkaListenerContainerFactory")
    public void consumeRecord(ConsumerRecords<?, ?> consumerRecords) {
        System.out.println("batch receive record");
        for (ConsumerRecord<?, ?> consumerRecord : consumerRecords) {
            System.out.println("current thread====>" + Thread.currentThread().getName() + "===>" + consumerRecord.value());
        }
    }
}
