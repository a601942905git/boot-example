package com.boot.example.async.task3;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * com.boot.example.async.task3.Task3
 *
 * @author lipeng
 * @date 2018/12/24 上午9:25
 */
@Component
public class Task3 extends AbstractFutureTask {

    @Async(value = "asyncTaskExecutor")
    @Override
    public Future<String> task1() throws InterruptedException {
        System.out.println("当前执行线程======" + Thread.currentThread().getName());
        return super.task1();
    }

    @Async(value = "asyncTaskExecutor")
    @Override
    public Future<String> task2() throws InterruptedException {
        System.out.println("当前执行线程======" + Thread.currentThread().getName());
        return super.task2();
    }

    @Async(value = "asyncTaskExecutor")
    @Override
    public Future<String> task3() throws InterruptedException {
        System.out.println("当前执行线程======" + Thread.currentThread().getName());
        return super.task3();
    }
}
