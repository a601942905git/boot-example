package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.boot.example.KafkaProducerController
 *
 * @author lipeng
 * @date 2020/2/22 下午6:39
 */
@RestController
@RequestMapping("/producer")
public class KafkaProducerController {

    public static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @Autowired
    private KafkaProducer<String, Object> kafkaProducer;

    @GetMapping("/send")
    public void sendMessage() {
        kafkaProducer.send("ack", "kafka producer send message" + ATOMIC_INTEGER.incrementAndGet());
    }
}
