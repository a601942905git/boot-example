package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * com.boot.example.command.SemaphoreCommand
 *
 * @author lipeng
 * @dateTime 2018/12/4 上午9:42
 */
public class SemaphoreCommand extends HystrixCommand<String> {

    private String name;

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
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        SemaphoreCommand semaphoreCommand = new SemaphoreCommand("semaphore-command");
        System.out.println(semaphoreCommand.execute());
        System.out.println("Main Thread:" + Thread.currentThread().getName());
    }
}
