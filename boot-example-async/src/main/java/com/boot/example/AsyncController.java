package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.AsyncController
 *
 * @author lipeng
 * @date 2019/10/9 下午4:23
 */
@RestController
@Slf4j
public class AsyncController {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/listReturnCallable")
    public Callable listReturnCallable() {
        log.info("listReturnCallable execute start......");
        Callable callable = this::doBusiness;
        log.info("listReturnCallable execute end......");
        return callable;
    }

    @GetMapping("/listReturnCompletableFuture")
    public CompletableFuture listReturnCompletableFuture() {
        log.info("listReturnCompletableFuture execute start......");
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(this::doBusiness, threadPoolTaskExecutor);
        log.info("listReturnCompletableFuture execute end......");
        return completableFuture;
    }

    @GetMapping("/listReturnDeferredResult")
    public DeferredResult listReturnDeferredResult() {
        log.info("listReturnDeferredResult execute start......");
        DeferredResult deferredResult = new DeferredResult(1000L);
        deferredResult.onTimeout(() -> log.info("execute timeout======>" + new Date()));
        deferredResult.onCompletion(() -> log.info("execute completion======>" + new Date()));
        threadPoolTaskExecutor.execute(() -> deferredResult.setResult(doBusiness()));
        log.info("listReturnDeferredResult execute start......");
        return deferredResult;
    }

    @GetMapping("/listReturnWebAsyncTask")
    public WebAsyncTask listReturnWebAsyncTask() {
        log.info("listReturnWebAsyncTask execute start......");
        WebAsyncTask webAsyncTask = new WebAsyncTask(this::doBusiness);
        log.info("listReturnWebAsyncTask execute start......");
        return webAsyncTask;
    }

    private List<Integer> doBusiness() {
        log.info("doBusiness execute start......");
        try {
            TimeUnit.SECONDS.sleep(3);
            List<Integer> numbers = new ArrayList<>();
            numbers.add(100001);
            numbers.add(100002);
            numbers.add(100003);
            numbers.add(100004);
            numbers.add(100005);
            log.info("doBusiness execute end......");
            return numbers;
        } catch (InterruptedException e) {
            return Collections.emptyList();
        }
    }
}
