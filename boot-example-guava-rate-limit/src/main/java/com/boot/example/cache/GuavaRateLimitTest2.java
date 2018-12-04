package com.boot.example.cache;

import com.google.common.util.concurrent.RateLimiter;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.cache.GuavaRateLimitTest2
 *
 * @author lipeng
 * @dateTime 2018/12/4 下午6:07
 */
public class GuavaRateLimitTest2 {


    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(1);

        /**
         * tryAcquire()根据给定的时间尝试获取令牌
         * 此处的时间是指100ms内能否获取到令牌，并不是等待100ms
         * 根据如上配置，每1s才产生一个令牌，所以tryAcquire()返回false
         */
        while (true) {
            Long time1 = Clock.systemDefaultZone().millis();
            boolean acquireFlag = rateLimiter.tryAcquire(100, TimeUnit.MILLISECONDS);
            Long time2 = Clock.systemDefaultZone().millis();
            System.out.println("【acquireFlag】" + acquireFlag +  "【时间差】:" + (time2 - time1));
        }
    }
}
