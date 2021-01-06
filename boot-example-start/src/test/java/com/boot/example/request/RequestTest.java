package com.boot.example.request;

import com.boot.example.StartApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.request.RequestTest
 *
 * @author lipeng
 * @dateTime 2018/11/23 上午10:47
 */
@SpringBootTest(classes = StartApplication.class)
public class RequestTest {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试StudentController
     */
    @Test
    public void testUserController() {
        String json = restTemplate.getForObject("http://localhost:8888/students/", String.class);
        System.out.println(json);
    }
}
