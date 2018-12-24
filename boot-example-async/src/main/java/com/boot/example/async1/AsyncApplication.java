package com.boot.example.async1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * com.boot.example.async1.AsyncApplication
 *
 * @author lipeng
 * @date 2018/12/24 上午10:32
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.boot.example")
public class AsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class, args);
    }
}
