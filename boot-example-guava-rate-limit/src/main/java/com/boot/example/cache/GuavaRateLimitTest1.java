package com.boot.example.cache;

import com.google.common.util.concurrent.RateLimiter;

/**
 * com.boot.example.cache.GuavaRateLimit
 *
 * @author lipeng
 * @dateTime 2018/12/4 下午5:46
 */
public class GuavaRateLimitTest1 {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 1s只允许处理10个请求，也就是每100ms创建一个令牌
         */
        RateLimiter rateLimiter = RateLimiter.create(10);

        /**
         * acquire()返回获取令牌的时间
         */
        for (int i = 0; i < 100; i++) {
            System.out.println(rateLimiter.acquire());
        }
    }
}
