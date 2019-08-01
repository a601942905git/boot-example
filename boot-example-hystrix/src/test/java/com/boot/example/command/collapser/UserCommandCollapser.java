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
public class UserCommandCollapser extends HystrixCollapser<List<User>, User, Integer> {

    private Integer userId;

    private UserService userService;

    public UserCommandCollapser(Integer userId, UserService userService) {
        super(Setter
                .withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCommandCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
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

        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f1 = new UserCommandCollapser(10001, userService).queue();
            Future<User> f2 = new UserCommandCollapser(10002, userService).queue();
            Future<User> f3 = new UserCommandCollapser(10003, userService).queue();
            Future<User> f4 = new UserCommandCollapser(10004, userService).queue();
            Future<User> f5 = new UserCommandCollapser(10005, userService).queue();

            f1.get();
            f2.get();
            f3.get();
            f4.get();
            f5.get();

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }
}
