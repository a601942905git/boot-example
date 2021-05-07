package com.boot.example;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.CacheApplication
 *
 * @author lipeng
 * @date 2021/3/9 5:05 PM
 */
@EnableMethodCache(basePackages = "com.boot.example")
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }
}
