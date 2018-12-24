package com.boot.example.async.task3;

import org.springframework.scheduling.annotation.AsyncResult;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.async.AbstractTask
 *
 * @author lipeng
 * @date 2018/12/24 上午9:06
 */
public class AbstractFutureTask {

    public Future<String> task1() throws InterruptedException {
        System.out.println("执行任务1");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        return new AsyncResult<>("任务一执行完成");
    }

    public Future<String> task2() throws InterruptedException {
        System.out.println("执行任务2");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        return new AsyncResult<>("任务一执行完成");
    }

    public Future<String> task3() throws InterruptedException {
        System.out.println("执行任务3");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        return new AsyncResult<>("任务一执行完成");
    }
}
