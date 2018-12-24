package com.boot.example.async.task3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Clock;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * com.boot.example.async.task3.Task3Application
 *
 * @author lipeng
 * @date 2018/12/24 上午9:29
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.boot.example.async")
@EnableAsync
public class Task3Application {

    private static Task3 task;

    @Autowired
    public void setTask3(Task3 task) {
        Task3Application.task = task;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpringApplication.run(Task3Application.class, args);
        Long time1 = Clock.systemDefaultZone().millis();
        Future<String> future1 = task.task1();
        Future<String> future2 = task.task2();
        Future<String> future3 = task.task3();

        future1.get();
        future2.get();
        future3.get();
        Long time2 = Clock.systemDefaultZone().millis();
        System.out.println("总消耗时间：" + (time2 - time1));
    }
}
