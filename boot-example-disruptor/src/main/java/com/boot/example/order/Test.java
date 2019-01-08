package com.boot.example.order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.order.Test
 *
 * @author lipeng
 * @date 2019/1/8 上午10:33
 */
public class Test {

    public static final int MAX_LOOP_COUNT = 100;

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);

        OrderTask orderTask = new OrderTask();

        scheduledExecutorService.scheduleAtFixedRate(orderTask, 10, 10, TimeUnit.MILLISECONDS);

        for (int i = 0; i < MAX_LOOP_COUNT; i++) {
            TimeUnit.MILLISECONDS.sleep(3);
            orderTask.addTask(Order.builder().id(i + 1).name("下订单" + (i + 1)).build());
        }
    }

    public static class OrderTask implements Runnable{
        List<Order> orderList = new ArrayList<>();

        public void addTask(Order order) {
            this.orderList.add(order);
        }

        @Override
        public void run() {
            System.out.println(orderList);
            System.out.println("=======================");
            orderList = new ArrayList<>();
        }
    }

}
