package com.boot.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.StartApplication
 *
 * @author lipeng
 * @dateTime 2018/11/21 下午7:27
 */
@SpringBootApplication
@MapperScan(basePackages = "com.boot.example")
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
