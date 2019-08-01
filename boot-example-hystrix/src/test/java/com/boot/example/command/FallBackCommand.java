package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;

/**
 * com.boot.example.command.FallBackCommand
 *
 * @author lipeng
 * @dateTime 2018/12/3 下午5:02
 */
public class FallBackCommand extends HystrixCommand<String> {

    private String name;

    public FallBackCommand(String name) {
        /**
         * 配置hystrix执行超时时间为500ms
         */
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("fallback"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500))
        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(1000);
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "服务超时，对服务进行降级";
    }

    @Test
    public void FallBackCommandTest() {
        FallBackCommand fallBackCommand = new FallBackCommand("fallback-hystrix");
        String fallBackResult = fallBackCommand.execute();
        System.out.println("FallBackCommand FallBackResult：" + fallBackResult);
    }
}
