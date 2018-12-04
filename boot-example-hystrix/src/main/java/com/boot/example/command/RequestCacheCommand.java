package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * com.boot.example.command.RequestCacheCommand
 *
 * @author lipeng
 * @dateTime 2018/12/3 下午5:37
 */
public class RequestCacheCommand extends HystrixCommand<String> {

    private Integer id;

    public RequestCacheCommand(Integer id) {
        super(HystrixCommandGroupKey.Factory.asKey("request-cache"));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        return "current thread " + Thread.currentThread().getName() + " execute " + id;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    public static void main(String[] args){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand command2a = new RequestCacheCommand(2);
            RequestCacheCommand command2b = new RequestCacheCommand(2);
            command2a.execute();
            command2b.execute();
            // isResponseFromCache判定是否是在缓存中获取结果
            System.out.println("是否从缓存中读取结果：" + command2a.isResponseFromCache());
            System.out.println("是否从缓存中读取结果：" + command2b.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        context = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand command3b = new RequestCacheCommand(2);
            System.out.println(command3b.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }
}
