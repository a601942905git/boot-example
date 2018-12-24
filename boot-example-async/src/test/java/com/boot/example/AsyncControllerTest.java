package com.boot.example;

import com.boot.example.async1.AsyncApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.AsyncControllerTest
 *
 * @author lipeng
 * @date 2018/12/24 上午10:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AsyncApplication.class)
public class AsyncControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testServletAsync() {
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/async/testAsync1", String.class);
        System.out.println("响应结果======>" + responseEntity.getBody());
    }

    @Test
    public void testCallable() {
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/async/testCallable", String.class);
        System.out.println("响应结果======>" + responseEntity.getBody());
    }

    @Test
    public void testCompletableFuture() {
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/async/testCompletableFuture", String.class);
        System.out.println("响应结果======>" + responseEntity.getBody());
    }

    @Test
    public void testDeferredResult() {
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/async/testDeferredResult", String.class);
        System.out.println("响应结果======>" + responseEntity.getBody());
    }

    @Test
    public void testWebAsyncTask() {
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/async/testWebAsyncTask", String.class);
        System.out.println("响应结果======>" + responseEntity.getBody());
    }
}
