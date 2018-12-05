package com.boot.example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.cache.GuavaCacheTest1
 *
 * @author lipeng
 * @dateTime 2018/12/4 下午4:33
 */
public class GuavaCacheTest4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 定义缓存，写入10s失效
         */
        LoadingCache<String, Student> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .refreshAfterWrite(5, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Student>() {
                    @Override
                    public Student load(String key) throws Exception {
                        Student student = Student.builder()
                                .id(10001)
                                .name("guava loading cache")
                                .build();
                        System.out.println("【加载数据并放入缓存，缓存key】:" + key);
                        return student;
                    }
                });


        while (true) {
            System.out.println(loadingCache.get("student-loading-cache"));
            Thread.sleep(1000);
        }
    }
}
