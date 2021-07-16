package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.ReactiveApplication
 *
 * 1.响应式编程是面向流的、异步开发方式
 * 2.Mono：包含0或1个元素的异步序列、Flux：包含1到多个元素的异步序列
 *
 * @author lipeng
 * @date 2021/7/14 1:27 PM
 */
@SpringBootApplication
public class ReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }
}
