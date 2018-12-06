package com.boot.example.command.collapser;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * com.boot.example.command.collapser.UserCommandCollapser
 *
 * @author lipeng
 * @dateTime 2018/12/6 下午3:18
 */
public class UserCommandCollapser1 extends HystrixCollapser<List<User>, User, Integer> {

    private Integer userId;

    private UserService userService;

    public UserCommandCollapser1(Integer userId, UserService userService) {
        super(Setter
                .withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCommandCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100))
                .andScope(Scope.GLOBAL));
        this.userId = userId;
        this.userService = userService;
    }

    @Override
    public Integer getRequestArgument() {
        return userId;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        List<Integer> userIdList = new ArrayList<>(collapsedRequests.size());
        userIdList.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserCommand(userIdList, userService);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        int count = 0;

        for (CollapsedRequest<User, Integer> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UserService userService = new UserService();
        Thread thread1 = new Thread(() -> {
            try {
                test1(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                test2(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                test3(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread4 = new Thread(() -> {
            try {
                test4(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread5 = new Thread(() -> {
            try {
                test5(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }

    public static void test1(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f1 = new UserCommandCollapser1(10001, userService).queue();

            System.out.println("【请求1】：" + f1.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test2(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f2 = new UserCommandCollapser1(10002, userService).queue();


            System.out.println("【请求2】：" + f2.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test3(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f3 = new UserCommandCollapser1(10003, userService).queue();


            System.out.println("【请求3】：" + f3.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test4(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f4 = new UserCommandCollapser1(10004, userService).queue();

            System.out.println("【请求4】：" + f4.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test5(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f5 = new UserCommandCollapser1(10005, userService).queue();


            System.out.println("【请求5】：" + f5.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }
}
