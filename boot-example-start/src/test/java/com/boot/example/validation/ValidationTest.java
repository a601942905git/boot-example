package com.boot.example.validation;

import com.boot.example.StartApplication;
import com.boot.example.core.entity.validation.ValidationEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.validation.ValidationTest
 *
 * @author lipeng
 * @dateTime 2018/11/24 下午2:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class ValidationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void validation() {
        String requestUrl = "http://localhost:8888/validations/";
        ValidationEntity entity = ValidationEntity.builder().build();
        restTemplate.postForObject(requestUrl, entity, String.class);
    }
}
