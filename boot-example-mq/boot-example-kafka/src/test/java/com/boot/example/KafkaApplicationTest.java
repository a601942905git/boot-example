package com.boot.example;

import com.boot.example.producer.KafkaMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * com.boot.example.KafkaApplicationTest
 *
 * @author lipeng
 * @date 2021/3/2 7:48 PM
 */
@SpringBootTest
public class KafkaApplicationTest {

    @Autowired
    private KafkaMessageSender<String, Object> kafkaSender;

    @Autowired
    private KafkaTemplate kafkaTemplate;
}
