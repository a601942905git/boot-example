package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * com.boot.example.command.HelloWorldCommand
 *
 * @author lipeng
 * @dateTime 2018/12/3 下午4:08
 */
public class HelloWorldCommand extends HystrixCommand<String> {

    private String name;

    public HelloWorldCommand(String name) {
        // 指定命令组名称
        super(HystrixCommandGroupKey.Factory.asKey("hello-world"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        /**
         * 每个命令对象实例只能调用一次，不能重复调用
         */
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Synchronous-hystrix");
        // 同步调用，执行run()方法
        String executeResult = helloWorldCommand.execute();
        System.out.println("HelloWorldCommand ExecuteResult:" + executeResult);

        helloWorldCommand = new HelloWorldCommand("Asynchronous-hystrix");
        // 异步调用
        Future<String> future = helloWorldCommand.queue();
        // 同步阻塞获取结果
        String futureResult = future.get(100, TimeUnit.SECONDS);
        System.out.println("HelloWorldCommand QueueResult:" + futureResult);

        System.out.println("Main Thread:" + Thread.currentThread().getName());
    }
}
