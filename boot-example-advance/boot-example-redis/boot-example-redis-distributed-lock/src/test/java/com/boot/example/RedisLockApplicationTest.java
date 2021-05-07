package com.boot.example;

import com.boot.example.redis.RedisLockService;
import com.boot.example.redis.RedisReentrantLockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.RedisApplicationTest
 *
 * @author lipeng
 * @date 2021/2/2 11:35 AM
 */
@SpringBootTest
@Slf4j
public class RedisLockApplicationTest {

    private static final Integer MAX_THREAD_SIZE = 20;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private RedisLockService redisService;

    @Autowired
    private RedisReentrantLockService redisReentrantLockService;

    @Test
    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_SIZE);

        for (int i = 0; i < MAX_THREAD_SIZE; i++) {
            executorService.submit(new Task(countDownLatch));
        }

        countDownLatch.countDown();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error("thread interrupted exception");
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testReentrantLock() {
        Boolean lockResult1 = redisReentrantLockService.lock("key2", 10, TimeUnit.SECONDS);
        log.info("current thread：{}，get redis lock result：{}", Thread.currentThread().getName(), lockResult1);

        Boolean lockResult2 = redisReentrantLockService.lock("key2", 10, TimeUnit.SECONDS);
        log.info("current thread：{}，get redis lock result：{}", Thread.currentThread().getName(), lockResult2);

        Boolean lockResult3 = redisReentrantLockService.lock("key2", 10, TimeUnit.SECONDS);
        log.info("current thread：{}，get redis lock result：{}", Thread.currentThread().getName(), lockResult3);

        Boolean unlockResult1 = redisReentrantLockService.unlock("key2");
        log.info("current thread：{}，release redis lock result：{}", Thread.currentThread().getName(), unlockResult1);

        Boolean unlockResult2 = redisReentrantLockService.unlock("key2");
        log.info("current thread：{}，release redis lock result：{}", Thread.currentThread().getName(), unlockResult2);

        Boolean unlockResult3 = redisReentrantLockService.unlock("key2");
        log.info("current thread：{}，release redis lock result：{}", Thread.currentThread().getName(), unlockResult3);
    }

    class Task implements Runnable {

        private final CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("thread interrupted exception");
                Thread.currentThread().interrupt();
            }

            try {
                log.info("current thread：{} execute task", Thread.currentThread().getName());

                // 加锁
                Boolean lockResult = redisService.lock("key1", 20, TimeUnit.SECONDS);
                log.info("current thread：{}，get redis lock result：{}", Thread.currentThread().getName(), lockResult);

                // 解锁
                Boolean unlockResult = redisService.unlock("key1");
                log.info("current thread：{}，release redis lock result：{}", Thread.currentThread().getName(), unlockResult);
            } catch (Exception e) {
                log.error("execute exception：", e);
            }
        }
    }
}
