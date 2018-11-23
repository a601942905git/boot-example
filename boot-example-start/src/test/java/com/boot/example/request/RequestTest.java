package com.boot.example.request;

import com.boot.example.StartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.request.RequestTest
 *
 * @author lipeng
 * @dateTime 2018/11/23 上午10:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
