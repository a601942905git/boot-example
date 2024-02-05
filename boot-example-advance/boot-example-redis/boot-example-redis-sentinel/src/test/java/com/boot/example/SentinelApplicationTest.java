package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author lipeng
 * @date 2022/12/26 13:37:28
 */
@SpringBootTest
@Slf4j
public class SentinelApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            stringRedisTemplate.opsForValue().set("middle-software" + (i + 1), "redis" + (i + 1));
            TimeUnit.SECONDS.sleep(3);
        }
    }
}
