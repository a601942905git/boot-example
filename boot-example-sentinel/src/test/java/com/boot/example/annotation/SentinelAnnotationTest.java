package com.boot.example.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.annotation.SentinelAnnotationTest
 *
 * @author lipeng
 * @dateTime 2018/12/5 上午10:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SentinelAnnotationApplication.class)
public class SentinelAnnotationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testSentinelAnnotation() {
        while (true) {
            ResponseEntity responseEntity =
                    restTemplate.getForEntity("http://localhost:8080/sentinel/", String.class);
            System.out.println(responseEntity.getBody());
        }
    }
}
