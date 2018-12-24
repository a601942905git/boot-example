package com.boot.example.async.task2;

import com.boot.example.async.AbstractTask;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.async.task2.Task2
 *
 * @author lipeng
 * @date 2018/12/24 上午9:17
 */
@Component
public class Task2 extends AbstractTask {

    @Async
    @Override
    public void task1() throws InterruptedException {
        super.task1();
    }

    @Async
    @Override
    public void task2() throws InterruptedException {
        super.task2();
    }

    @Async
    @Override
    public void task3() throws InterruptedException {
        super.task3();
    }
}
