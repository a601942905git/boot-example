package com.boot.example;

import com.boot.example.util.ApplicationContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.FrameworkLearningApplication
 *
 * @author lipeng
 * @date 2019/12/31 下午1:28
 */
@SpringBootApplication
public class FrameworkLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrameworkLearningApplication.class, args);
        System.out.println("applicationContext======>" + ApplicationContextUtils.getApplicationContext());
        System.out.println("environment======>" + ApplicationContextUtils.getEnvironment());
    }
}
