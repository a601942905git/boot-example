package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.AsyncTest
 *
 * @author lipeng
 * @date 2019/10/9 上午10:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootAsyncApplication.class)
@Slf4j
public class AsyncTest {

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void syncTask() throws InterruptedException {
        log.info("main thread execute start......");
        asyncTask.syncTask();
        log.info("main thread execute end......");
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void asyncTask() throws InterruptedException {
        log.info("main thread execute start......");
        asyncTask.asyncTask();
        log.info("main thread execute end......");
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void asyncTaskWithCustomThreadPool() throws InterruptedException {
        log.info("main thread execute start......");
        asyncTask.asyncTaskWithCustomThreadPool();
        log.info("main thread execute end......");
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void asyncTaskWithFutureReturn() throws InterruptedException, ExecutionException {
        log.info("main thread execute start......");
        log.info("asyncTaskWithFutureReturn execute result：{}", asyncTask.asyncTaskWithFutureReturn().get());
        log.info("main thread execute end......");
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void asyncTaskWithCompletableFutureReturn() throws InterruptedException {
        log.info("main thread execute start......");
        asyncTask.asyncTaskWithCompletableFutureReturn().whenComplete(
                (result, ex) -> log.info("asyncTaskWithCompletableFutureReturn execute result：{}", result));
        log.info("main thread execute end......");
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void asyncTaskWithListenableFutureReturn() throws InterruptedException, ExecutionException {

        log.info("main thread execute start......");
        log.info("main thread execute end......");
        asyncTask.asyncTaskWithListenableFutureReturn().addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("asyncTaskWithListenableFutureReturn execute fail：", ex);
            }

            @Override
            public void onSuccess(Object result) {
                log.info("asyncTaskWithListenableFutureReturn execute success result：{}", result);
            }
        });
        TimeUnit.SECONDS.sleep(3);
    }
}
