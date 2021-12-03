package com.boot.example;

import com.boot.example.constant.TopicConstant;
import com.boot.example.entity.Student;
import com.boot.example.producer.KafkaSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.KafkaApplicationTest
 *
 * @author lipeng
 * @date 2021/3/2 7:48 PM
 */
@SpringBootTest
public class KafkaApplicationTest {

    @Autowired
    private KafkaSender<String, Object> kafkaSender;

    @Test
    public void sendSingleMessage() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            kafkaSender.sendSimpleMessage(TopicConstant.FIRST_TOPIC_NAME, "kafka send single message：" + (i + 1));
            TimeUnit.SECONDS.sleep(1);
        }
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void sendComplexMessage() throws InterruptedException {
        Student student = Student.builder()
                .id(10001)
                .name("test")
                .age(22)
                .build();
        kafkaSender.sendComplexMessage(TopicConstant.SECOND_TOPIC_NAME, student);
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void sendMessage() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            kafkaSender.sendSimpleMessage(TopicConstant.THIRD_TOPIC_NAME, "message_" + i);
        }

        // 休眠10s保证发送的消息被消费者消费
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void sendExceptionMessage() throws InterruptedException {
        kafkaSender.sendSimpleMessage(TopicConstant.FOUR_TOPIC_NAME, "send exception message");
        // 休眠10s保证发送的消息被消费者消费
        TimeUnit.SECONDS.sleep(10);
    }
}
