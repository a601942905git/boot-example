package com.boot.example.consumer;

import com.boot.example.constant.TopicConstant;
import com.boot.example.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * com.boot.example.consumer.KafkaConsumer
 *
 * @author lipeng
 * @date 2020/2/20 下午9:21
 */
@Component
@Slf4j
public class KafkaConsumer {

    /**
     * 当传入Acknowledgment参数是，需要指定监听器的模式为手动模式
     * @see ContainerProperties.AckMode
     *
     * 1.定义三个消费者订阅主题为：first-topic的消息
     * 2.第1个和第2个消费者属于同一消费者组，那么同一个分区中的消息只能被该消费者组中的一个消费者消费。
     *   可以通过执行com.boot.example.KafkaApplicationTest#sendSingleMessage()单元测试进行观察
     * 3. 执行结果1：
     *      receive simple message1 content：
     *      receive simple message3 content：
     *    执行结果2：
     *      receive simple message1 content：
     *      receive simple message3 content：
     *
     * @param content 消息内容
     * @param ack ack对象
     */
    @KafkaListener(topics = TopicConstant.FIRST_TOPIC_NAME, groupId = "consumer_first1")
    public void consumeSimpleMessage1(String content, Acknowledgment ack) {
        log.error("receive simple message1 content：{}", content);
        ack.acknowledge();
    }

    @KafkaListener(topics = TopicConstant.FIRST_TOPIC_NAME, groupId = "consumer_first1")
    public void consumeSimpleMessage2(String content, Acknowledgment ack) {
        log.error("receive simple message2 content：{}", content);
        ack.acknowledge();
    }

    @KafkaListener(topics = TopicConstant.FIRST_TOPIC_NAME, groupId = "consumer_first2")
    public void consumeSimpleMessage3(String content, Acknowledgment ack) {
        log.error("receive simple message3 content：{}", content);
        ack.acknowledge();
    }


    @KafkaListener(topics = TopicConstant.SECOND_TOPIC_NAME, groupId = "consumer_second1")
    public void consumeComplexMessage1(Student student, Acknowledgment ack) {
        log.error("receive complex message1 content：{}", student);
        ack.acknowledge();
    }

    @KafkaListener(topics = TopicConstant.SECOND_TOPIC_NAME, groupId = "consumer_second2")
    public void consumeComplexMessage2(ConsumerRecord<String, Student> consumerRecord, Acknowledgment ack) {
        log.error("receive complex message2 content：{}", consumerRecord);
        ack.acknowledge();
    }

    @KafkaListener(topics = TopicConstant.SECOND_TOPIC_NAME, groupId = "consumer_second3",
            containerFactory = "batchKafkaListenerContainerFactory")
    public void batchConsumeComplexMessage(ConsumerRecords<String, Student> consumerRecords, Acknowledgment ack) {
        log.error("batch receive complex message content count：{}", consumerRecords.count());
        ack.acknowledge();
    }

    /**
     *
     * @param consumerRecords
     * @param ack
     */
    @KafkaListener(topics = TopicConstant.THIRD_TOPIC_NAME, groupId = "consumer_third1",
            containerFactory = "batchKafkaListenerContainerFactory")
    public void batchConsumeMessage(ConsumerRecords<String, String> consumerRecords, Acknowledgment ack) {
        for (ConsumerRecord<String, String> record : consumerRecords) {
            String messageDetail = String.format("message partition：%d, message offset：%d, message：%s",
                    record.partition(), record.offset(), record.value());
            log.info("receive message detail：{}", messageDetail);

            // 模拟业务异常
            if (Objects.equals("message_6", record.value())) {
                throw new RuntimeException("handle message exception: " + messageDetail);
            }
        }

        // 手动提交消息偏移量
        ack.acknowledge();
    }
}
