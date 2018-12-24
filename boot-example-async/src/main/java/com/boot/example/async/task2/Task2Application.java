package com.boot.example.async.task2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * com.boot.example.async.task2.Task2Application
 *
 * @author lipeng
 * @date 2018/12/24 上午9:18
 */
@SpringBootApplication
@EnableAsync
public class Task2Application {

    private static Task2 task;

    @Autowired
    public void setTask2(Task2 task) {
        Task2Application.task = task;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Task2Application.class, args);
        task.task1();
        task.task2();
        task.task3();
        System.out.println("主线程执行完毕");
    }
}
