package com.boot.example.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;

/**
 * com.boot.example.command.ObservableCommand
 *
 * @author lipeng
 * @dateTime 2018/12/3 下午4:48
 */
public class ObservableCommand extends HystrixCommand<String> {

    private String name;

    public ObservableCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("observable"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        ObservableCommand observableCommand = new ObservableCommand("observable-hystrix");
        // 注册观察者事件
        Observable<String> observableResult = observableCommand.observe();
        // 注册结果回调事件
        observableResult.subscribe(result -> {
            System.out.println("HelloWorldCommand observableResult:" + result);
        });

        observableResult.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("HelloWorldCommand observableResult执行完成");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("HelloWorldCommand observableResult执行异常，异常信息：" + throwable.getMessage());
            }

            @Override
            public void onNext(String result) {
                System.out.println("HelloWorldCommand observableResult执行结果：" + result);
            }
        });
    }
}
