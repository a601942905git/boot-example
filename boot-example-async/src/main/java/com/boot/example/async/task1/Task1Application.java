package com.boot.example.async.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Clock;

/**
 * com.boot.example.async.task1.Task1Application
 *
 * @author lipeng
 * @date 2018/12/24 上午9:11
 */
@SpringBootApplication
public class Task1Application {

    private static Task1 task;

    @Autowired
    public void setTask1(Task1 task) {
        Task1Application.task = task;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Task1Application.class, args);
        long time1 = Clock.systemDefaultZone().millis();
        task.task1();
        task.task2();
        task.task3();
        long time2 = Clock.systemDefaultZone().millis();
        System.out.println("执行消耗时间：" + (time2 - time1));
    }
}
