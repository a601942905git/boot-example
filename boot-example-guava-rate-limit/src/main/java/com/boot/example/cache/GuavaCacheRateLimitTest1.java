package com.boot.example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * com.boot.example.cache.GuavaCacheRateLimit
 * 使用GuavaCache来实现时间窗口限流
 * @author lipeng
 * @dateTime 2018/12/4 下午5:01
 */
public class GuavaCacheRateLimitTest1 {

    public static final Integer TIME_WINDOW = 10;

    public static final Integer TIME_WINDOW_MAX_REQUEST_COUNT = 10;

    public static final LongAdder LONG_ADDER = new LongAdder();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String, AtomicInteger> rateLimitCache = CacheBuilder.newBuilder()
                .expireAfterWrite(TIME_WINDOW, TimeUnit.SECONDS)
                .build(new CacheLoader<String, AtomicInteger>() {
                    @Override
                    public AtomicInteger load(String key) throws Exception {
                        return new AtomicInteger(0);
                    }
                });

        while (true) {
            AtomicInteger rateLimit = rateLimitCache.get("rate-limit");
            if (rateLimit.incrementAndGet() > TIME_WINDOW_MAX_REQUEST_COUNT) {
                System.out.println("【开始执行限流操作。。。。。。】" + rateLimit.get());
                break;
            }
            System.out.println("【执行请求】");
        }

    }
}
