package com.boot.example.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.cache.GuavaCacheTest2
 *
 * @author lipeng
 * @dateTime 2018/12/4 下午4:52
 */
public class GuavaCacheTest3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * maximumSize指定缓存元素的个数，本示例指定2个，如果超过2个，就会触发删除监听事件
         */
        Cache<String, Student> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        System.out.println("【key】:" + notification.getKey() + "被移除");
                    }
                })
                .build();

        cache.get("student-callable-cache1", () -> {
            System.out.println("【加载数据并放入缓存，缓存key】:student-callable-cache1");
            return Student.builder()
                    .id(10002)
                    .name("guava callable cache1")
                    .build();
        });

        cache.get("student-callable-cache2", () -> {
            System.out.println("【加载数据并放入缓存，缓存key】:student-callable-cache2");
            return Student.builder()
                    .id(10003)
                    .name("guava callable cache2")
                    .build();
        });

        cache.get("student-callable-cache3", () -> {
            System.out.println("【加载数据并放入缓存，缓存key】:student-callable-cache3");
            return Student.builder()
                    .id(10004)
                    .name("guava callable cache3")
                    .build();
        });
    }
}
