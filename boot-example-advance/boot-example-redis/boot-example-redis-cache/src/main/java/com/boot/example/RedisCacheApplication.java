package com.boot.example;

import com.boot.example.annotation.EnableCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.RedisCacheApplication
 *
 * @author lipeng
 * @date 2021/6/1 8:04 PM
 */
@SpringBootApplication
@EnableCache
public class RedisCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisCacheApplication.class, args);
    }
}
