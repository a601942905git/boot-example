package com.boot.example;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.GuavaRateLimitTest
 *
 * @author lipeng
 * @date 2020/3/31 11:39 AM
 */
@Slf4j
public class GuavaRateLimitTest {

    @Test
    public void testSmoothBursty() {
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            log.info("get 1 tokens: " + r.acquire() + "s");
        }
        /**
         * output: 基本上都是0.2s执行一次，符合一秒发放5个令牌的设定。
         * get 1 tokens: 0.0s
         * get 1 tokens: 0.182014s
         * get 1 tokens: 0.188464s
         * get 1 tokens: 0.198072s
         * get 1 tokens: 0.196048s
         * get 1 tokens: 0.197538s
         * get 1 tokens: 0.196049s
         */
    }

    @Test
    public void testSmoothBursty2() {
        RateLimiter r = RateLimiter.create(2);
        while (true)
        {
            log.info("get 1 tokens: " + r.acquire(1) + "s");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
            log.info("get 1 tokens: " + r.acquire(1) + "s");
            log.info("get 1 tokens: " + r.acquire(1) + "s");
            log.info("get 1 tokens: " + r.acquire(1) + "s");
            log.info("end");
            /**
             * RateLimiter可以储存令牌，一定时间未使用，后续对令牌的请求可能不需要进行等待
             *
             * output:
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * end
             * get 1 tokens: 0.499796s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             * get 1 tokens: 0.0s
             */
        }
    }

    @Test
    public void testSmoothBursty3() {
        RateLimiter r = RateLimiter.create(5);
        while (true)
        {
            System.out.println("get 5 tokens: " + r.acquire(5) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /**
             * output:
             * get 5 tokens: 0.0s
             * get 1 tokens: 0.996766s 滞后效应，需要替前一个请求进行等待
             * get 1 tokens: 0.194007s
             * get 1 tokens: 0.196267s
             * end
             * get 5 tokens: 0.195756s
             * get 1 tokens: 0.995625s 滞后效应，需要替前一个请求进行等待
             * get 1 tokens: 0.194603s
             * get 1 tokens: 0.196866s
             */
        }
    }

    @Test
    public void testSmoothWarmingUp() {
        RateLimiter rateLimiter = RateLimiter.create(2, 3, TimeUnit.SECONDS);
        while (true) {
            System.out.println("get 1 tokens: " + rateLimiter.acquire(1) + "s");
            System.out.println("get 1 tokens: " + rateLimiter.acquire(1) + "s");
            System.out.println("get 1 tokens: " + rateLimiter.acquire(1) + "s");
            System.out.println("get 1 tokens: " + rateLimiter.acquire(1) + "s");
            System.out.println("end");
        }
        /**
         * 指定生成令牌的速率为500ms一个，在3s预热时间内可能会超过500ms，在预热结束后进入平滑状态
         *
         * get 1 tokens: 0.0s
         * get 1 tokens: 1.331247s
         * get 1 tokens: 0.994234s
         * get 1 tokens: 0.664657s
         * end
         * get 1 tokens: 0.498622s
         * get 1 tokens: 0.499182s
         * get 1 tokens: 0.497311s
         * get 1 tokens: 0.499885s
         * end
         */
    }

    @Test
    public void test1() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(1);
        while (true) {
            new Thread(new Task(rateLimiter)).start();
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    public static class Task implements Runnable{

        private RateLimiter rateLimiter;

        public Task(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
        }

        @Override
        public void run() {
            if (!rateLimiter.tryAcquire()) {
                log.error("系统繁忙，请稍后重试...");
                return;
            }

            log.info(Thread.currentThread().getName() + "，执行业务");
        }
    }

}
