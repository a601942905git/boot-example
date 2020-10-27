package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.AsyncTask
 *
 * @author lipeng
 * @date 2019/10/9 上午9:59
 */
@Component
@Slf4j
public class AsyncTask {

    public void syncTask() throws InterruptedException {
        log.info("sync task execute start，current thread name：{}......", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        log.info("sync task execute end，current thread name：{}......", Thread.currentThread().getName());
    }

    @Async
    public void asyncTask() throws InterruptedException {
        log.info("async task execute start，current thread name：{}......", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        log.info("async task execute end，current thread name：{}......", Thread.currentThread().getName());
    }

    @Async
    public void asyncTaskWithCustomThreadPool() throws InterruptedException {
        log.info("async task with custom thread pool execute start，current thread name：{}......", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        log.info("async task with custom thread pool execute end，current thread name：{}......", Thread.currentThread().getName());
    }

    @Async
    public Future asyncTaskWithFutureReturn() throws InterruptedException {
        log.info("sync task execute start，current thread name：{}......", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        log.info("sync task execute end，current thread name：{}......", Thread.currentThread().getName());
        return new AsyncResult("asyncTaskWithFutureReturn");
    }

    @Async
    public CompletableFuture asyncTaskWithCompletableFutureReturn() throws InterruptedException {
        log.info("async task execute start，current thread name：{}......", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        log.info("async task execute end，current thread name：{}......", Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> "asyncTaskWithCompletableFutureReturn");
    }

    @Async
    public ListenableFuture asyncTaskWithListenableFutureReturn() throws InterruptedException {
        log.info("async task with custom thread pool execute start，current thread name：{}......", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        log.info("async task with custom thread pool execute end，current thread name：{}......", Thread.currentThread().getName());
        return new AsyncResult("asyncTaskWithCustomThreadPool");
    }

}
