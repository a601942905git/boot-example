package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * com.boot.example.AsyncControllerTest
 *
 * @author lipeng
 * @date 2018/12/24 上午10:34
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootAsyncApplication.class)
public class AsyncControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testListReturnCallable() {
        ResponseEntity<List> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/listReturnCallable", List.class);
        log.info("response result======>" + responseEntity.getBody());
    }

    @Test
    public void testListReturnCompletableFuture() {
        ResponseEntity<List> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/listReturnCompletableFuture", List.class);
        log.info("response result======>" + responseEntity.getBody());
    }

    @Test
    public void testListReturnDeferredResult() {
        ResponseEntity<List> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/listReturnDeferredResult", List.class);
        log.info("response result======>" + responseEntity.getBody());
    }

    @Test
    public void testListReturnWebAsyncTask() {
        ResponseEntity<List> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/listReturnWebAsyncTask", List.class);
        System.out.println("response result======>" + responseEntity.getBody());
    }
}
