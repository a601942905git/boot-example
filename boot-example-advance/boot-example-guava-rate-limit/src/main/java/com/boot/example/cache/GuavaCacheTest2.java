package com.boot.example.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.cache.GuavaCacheTest2
 *
 * @author lipeng
 * @dateTime 2018/12/4 下午4:52
 */
public class GuavaCacheTest2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Cache<String, Student> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        while (true) {
            Student student = cache.get("student-callable-cache", () -> {
                System.out.println("【加载数据并放入缓存，缓存key】:student-callable-cache");
                return Student.builder()
                        .id(10002)
                        .name("guava callable cache")
                        .build();
            });

            System.out.println(student);
            Thread.sleep(1000);
        }

    }
}
