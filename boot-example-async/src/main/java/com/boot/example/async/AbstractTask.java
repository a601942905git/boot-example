package com.boot.example.async;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.async.AbstractTask
 *
 * @author lipeng
 * @date 2018/12/24 上午9:06
 */
public class AbstractTask {

    public void task1() throws InterruptedException {
        System.out.println("执行任务1");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
    }

    public void task2() throws InterruptedException {
        System.out.println("执行任务2");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
    }

    public void task3() throws InterruptedException {
        System.out.println("执行任务3");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
    }
}
