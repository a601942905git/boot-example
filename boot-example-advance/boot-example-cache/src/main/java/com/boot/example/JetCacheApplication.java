package com.boot.example;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.JetCacheApplication
 *
 * @author lipeng
 * @dateTime 2018/11/30 下午2:10
 */
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.boot.example")
public class JetCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(JetCacheApplication.class, args);
    }
}
