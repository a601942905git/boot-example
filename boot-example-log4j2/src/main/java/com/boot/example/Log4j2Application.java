package com.boot.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * com.boot.example.Log4j2Application
 *
 * @author lipeng
 * @date 2019/1/9 下午2:54
 */
@SpringBootApplication
@EnableScheduling
public class Log4j2Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log4j2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Log4j2Application.class, args);
    }
}
