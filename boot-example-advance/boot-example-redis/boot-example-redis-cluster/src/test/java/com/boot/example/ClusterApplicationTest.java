package com.boot.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * com.boot.example.ClusterApplicationTest
 *
 * @author lipeng
 * @date 2021/5/10 4:19 PM
 */
@SpringBootTest
public class ClusterApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        for (int i = 0; i < 100; i++) {
            valueOperations.set("key" + (i + 1), "value" + (i + 1));
        }
    }

    @Test
    public void testGet() {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.get("key1");
        valueOperations.get("key2");
        valueOperations.get("key3");
        valueOperations.get("key4");
        valueOperations.get("key5");
    }
}
