package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.command.RequestErrorCommand
 * 模拟10s内发送20个请求，并且这20个请求都直接抛出异常
 * 触发熔断器开关打开，之后5s内所有的请求全部拒绝
 * 通过线程等待6s当熔断器开关关闭的时候将请求发送出去
 *
 * @author lipeng
 * @dateTime 2018/12/6 下午1:21
 */
public class RequestErrorCommand extends HystrixCommand<String> {

    private String name;

    private Integer count;

    public RequestErrorCommand(String name, Integer count) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestErrorCommand"));
        this.name = name;
        this.count = count;
    }

    @Override
    protected String run() throws Exception {
        if (count < 20) {
            throw new RuntimeException("【模拟调用失败】");
        }
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "【调用服务阻塞】";
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 25; i++) {
            TimeUnit.MILLISECONDS.sleep(100);
            RequestErrorCommand requestErrorCommand = new RequestErrorCommand("error", i);
            System.out.println(requestErrorCommand.execute() + "======>" + i + "===>【熔断器是否打开】：" + requestErrorCommand.isCircuitBreakerOpen());
        }

        // Thread.sleep(3500);
        RequestErrorCommand requestErrorCommand = new RequestErrorCommand("error", 1000);
        System.out.println("熔断器是否打开：" + requestErrorCommand.isCircuitBreakerOpen());
        System.out.println(requestErrorCommand.execute());
    }
}
