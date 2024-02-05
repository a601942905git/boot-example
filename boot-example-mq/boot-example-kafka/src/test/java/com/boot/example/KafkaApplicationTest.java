package com.boot.example;

import com.boot.example.entity.ProductRequest;
import com.boot.example.producer.KafkaMessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private KafkaMessageSender<String, Object> kafkaSender;

    @Test
    public void send() throws InterruptedException {
        kafkaSender.sendSimpleMessage("product", "hello world");
        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    public void send1() throws InterruptedException, JsonProcessingException {
        for (int i = 0; i < 3; i++) {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setMsgNo(i + 1 + "");
            ProductRequest.Product product = new ProductRequest.Product();
            product.setCode(1000 + i + 1 + "");
            product.setName("product" + i + 1);
            productRequest.setProduct(product);
            kafkaSender.sendSimpleMessage("product", new ObjectMapper().writeValueAsString(productRequest));
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
