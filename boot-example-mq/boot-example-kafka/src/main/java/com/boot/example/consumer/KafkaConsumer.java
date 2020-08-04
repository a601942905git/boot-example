package com.boot.example.consumer;

import com.boot.example.entity.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.boot.example.consumer.KafkaConsumer
 *
 * @author lipeng
 * @date 2020/2/20 下午9:21
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "first", groupId = "consumer_first")
    public void consume(String content, Acknowledgment ack) {
        System.out.println("receive content：" + content);
        ack.acknowledge();
    }

    @KafkaListener(topics = "second", groupId = "consumer_student")
    public void consumeStudent(Student student, Acknowledgment ack) {
        System.out.println("receive student：" + student);
        ack.acknowledge();
    }

    @KafkaListener(topics = "third", groupId = "consumer_third")
    public void consumeRecord(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
        System.out.println("receive record：" + consumerRecord);
        ack.acknowledge();
    }

    /**
     * 批量消息记录
     *
     * @param consumerRecords 消费者记录信息
     */
    @KafkaListener(topics = "batch", groupId = "consumer_batch", containerFactory = "batchKafkaListenerContainerFactory")
    public void consumeRecord(ConsumerRecords<?, ?> consumerRecords, Acknowledgment ack) {
        System.out.println("batch receive record");
        for (ConsumerRecord<?, ?> consumerRecord : consumerRecords) {
            System.out.println("current thread====>" + Thread.currentThread().getName() + "===>" + consumerRecord.value());
        }
        ack.acknowledge();
    }

    public static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    /**
     * 生产者发送3条消息，第三条消息业务处理，重新拉取
     *
     * receive content：kafka producer send message1
     * receive content：kafka producer send message2
     * receive content：kafka producer send message3
     * 2020-02-22 19:07:35.212  INFO 19914 --- [ntainer#3-0-C-1] o.a.k.clients.consumer.KafkaConsumer     : [Consumer clientId=consumer-4, groupId=ack] Seeking to offset 1 for partition ack-1
     * receive content：kafka producer send message3
     * @param content
     * @param ack
     */
    @KafkaListener(topics = "ack", groupId = "ack")
    public void consumeAck(String content, Acknowledgment ack) {
        try {
            System.out.println("receive content：" + content);
            if (ATOMIC_INTEGER.incrementAndGet() == 3) {
                // 模拟业务异常
                throw new NullPointerException();
            } else {
                // 业务正常执行完成
                ack.acknowledge();
            }
        } catch (Exception e) {
            // 处理失败，重新拉取处理失败的消息
            ack.nack(10);
        }
    }
}
