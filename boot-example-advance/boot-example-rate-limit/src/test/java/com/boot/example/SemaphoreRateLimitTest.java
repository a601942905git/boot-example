package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.SemaphoreRatelimit
 *
 * 基于Semaphore实现简单限流，可以限制系统同时处理的请求数量
 *
 * @author lipeng
 * @date 2020/3/31 11:23 AM
 */
@Slf4j
public class SemaphoreRateLimitTest {

    @Test
    public void test() throws InterruptedException {
        // 定义一个含有5个令牌的对象
        Semaphore semaphore = new Semaphore(5);
        while (true) {
            TimeUnit.MILLISECONDS.sleep(500);
            new Thread(new Task(semaphore)).start();
        }
    }

    public static class Task implements Runnable {

        private Semaphore semaphore;

        public Task(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            // 如果获取到令牌则执行任务，否则不执行
            if (!semaphore.tryAcquire()) {
                log.error("系统繁忙，请稍后重试");
                return;
            }

            try {
                log.info(Thread.currentThread().getName() + "，开始执行任务");
                // 模拟正常业务
                TimeUnit.SECONDS.sleep(3);
                log.info(Thread.currentThread().getName() + "，任务执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

}
