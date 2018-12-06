package com.boot.example.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * com.boot.example.annotation.SentinelAnnotationApplication
 *
 * @author lipeng
 * @dateTime 2018/12/5 上午10:47
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.boot.example")
public class SentinelAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelAnnotationApplication.class, args);
    }
}
