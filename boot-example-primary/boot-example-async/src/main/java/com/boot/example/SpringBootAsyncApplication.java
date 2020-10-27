package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * com.boot.example.SpringBootAsyncApplication
 *
 * @author lipeng
 * @date 2019/10/9 上午9:58
 */
@SpringBootApplication
@EnableAsync
public class SpringBootAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncApplication.class, args);
    }
}
