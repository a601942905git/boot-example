package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.CustomerStarterTestApplication
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午6:05
 */
@SpringBootApplication
public class CustomerStarterTestApplication implements CommandLineRunner {

    @Autowired
    private Greeter greeter;

    public static void main(String[] args) {
        SpringApplication.run(CustomerStarterTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("【自定义Starter】：" + greeter.greet());
    }
}
