package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * com.boot.example.command.PropertiesCommand
 *
 * @author lipeng
 * @dateTime 2018/12/3 下午5:18
 */
public class PropertiesCommand extends HystrixCommand<String> {

    private String name;

    public PropertiesCommand(String name) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("order-group"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("get-order-detail"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("http-order-detail"))
        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }


    public static void main(String[] args) {
        PropertiesCommand propertiesCommand = new PropertiesCommand("properties-hystrix");
        String result = propertiesCommand.execute();
        System.out.println(result);
    }
}
