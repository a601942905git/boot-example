package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.command.SemaphoreCommand
 *
 * @author lipeng
 * @dateTime 2018/12/4 上午9:42
 */
public class SemaphoreCommand extends HystrixCommand<String> {

    CountDownLatch countDownLatch = new CountDownLatch(20);

    private String name;

    /**
     * 信号量隔离，并发请求数为10
     * @param name
     */
    public SemaphoreCommand(String name) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("semaphore"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter().withExecutionIsolationStrategy(
                                HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE
                        )
                )
        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(100);
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "【调用阻塞】：" + Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        SemaphoreCommand semaphoreCommand = new SemaphoreCommand("semaphore-command");
        semaphoreCommand.testExecute();
    }

    public void testExecute() {
        for (int i = 0; i < 20; i++) {
            new Thread(new Task(countDownLatch)).start();
        }
    }

    public class Task implements Runnable {

        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SemaphoreCommand semaphoreCommand = new SemaphoreCommand("semaphore-command");
            System.out.println(semaphoreCommand.execute());
        }
    }

}
