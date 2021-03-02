package com.boot.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.consumer.KafkaConsumer
 *
 * @author lipeng
 * @date 2020/2/20 下午9:21
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "first", groupId = "consumer_first", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String content, Acknowledgment ack) {
        log.info("receive content：" + content);
        ack.acknowledge();
    }

//    @KafkaListener(topics = "second", groupId = "consumer_student")
//    public void consumeStudent(Student student, Acknowledgment ack) {
//        log.info("receive student：" + student);
//        ack.acknowledge();
//    }
//
//    @KafkaListener(topics = "third", groupId = "consumer_third")
//    public void consumeRecord(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
//        log.info("receive record：" + consumerRecord);
//        ack.acknowledge();
//    }
}
