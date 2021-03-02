package com.boot.example;

import com.boot.example.entity.Student;
import com.boot.example.producer.KafkaProducer;
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
    private KafkaProducer<String, Object> kafkaProducer;

    @Test
    public void sendSingleMessage() throws InterruptedException {
        kafkaProducer.send("first", "kafka send single message");
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void sendMessage() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send("first", "kafka send message：" + i);
        }
        TimeUnit.SECONDS.sleep(1000);
    }

    @Test
    public void sendStudentMessage() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Student student = Student.builder()
                    .id(i + 1)
                    .name("student" + (i + 1))
                    .age((i + 1))
                    .build();
            kafkaProducer.send("second", student);
        }
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void sendRecord() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send("third", "record" + (i + 1));
        }
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void sendStudentRecord() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Student student = Student.builder()
                    .id(i + 1)
                    .name("student" + (i + 1))
                    .age((i + 1))
                    .build();
            kafkaProducer.send("third", student);
        }
        TimeUnit.SECONDS.sleep(10);
    }
}
