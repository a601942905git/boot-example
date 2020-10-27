package com.boot.example.command.collapser;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 * com.boot.example.command.collapser.UserCommand
 *
 * @author lipeng
 * @dateTime 2018/12/6 下午3:25
 */
public class UserCommand extends HystrixCommand<List<User>> {

    private List<Integer> userIdList;

    private UserService userService;

    public UserCommand(List<Integer> userIdList, UserService userService) {
        super(HystrixCommandGroupKey.Factory.asKey("UserCommand"));
        this.userIdList = userIdList;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        List<User> userList = userService.listUser(userIdList);
        System.out.println(userList);
        return userList;
    }
}
