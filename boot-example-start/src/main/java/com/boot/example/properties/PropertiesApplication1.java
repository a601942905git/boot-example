package com.boot.example.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * com.boot.example.properties.PropertiesApplication1
 *
 * @author lipeng
 * @dateTime 2018/11/22 上午11:24
 */
@SpringBootApplication
public class PropertiesApplication1 {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(PropertiesApplication1.class, args);
        Environment environment = applicationContext.getEnvironment();

        System.out.println("编号======>" + environment.getProperty("com.person.id"));
        System.out.println("名称======>" + environment.getProperty("com.person.name"));
        System.out.println("年龄======>" + environment.getProperty("com.person.age"));
    }
}
