package com.boot.example.async.task1;

import com.boot.example.async.AbstractTask;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.async.task1.AbstractTask
 *
 * @author lipeng
 * @date 2018/12/24 上午9:09
 */
@Component
public class Task1 extends AbstractTask {

    @Override
    public void task1() throws InterruptedException {
        super.task1();
    }

    @Override
    public void task2() throws InterruptedException {
        super.task2();
    }

    @Override
    public void task3() throws InterruptedException {
        super.task3();
    }
}
