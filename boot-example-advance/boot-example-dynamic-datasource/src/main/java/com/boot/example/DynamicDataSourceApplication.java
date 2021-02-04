package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * com.boot.example.DynamicDataSourceApplication
 *
 * 需要排除DataSourceAutoConfiguration自动配置类，否则会出现循环依赖情况
 *
 * @author lipeng
 * @date 2021/2/3 6:13 PM
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DynamicDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceApplication.class, args);
    }
}
