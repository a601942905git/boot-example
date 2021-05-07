package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.RedisApplication
 *
 * @author lipeng
 * @date 2021/2/1 5:17 PM
 */
@SpringBootApplication
public class RedisDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDistributedLockApplication.class, args);
    }
}
