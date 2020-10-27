package com.boot.example.command.collapser;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.command.collapser.UserService
 *
 * @author lipeng
 * @dateTime 2018/12/6 下午3:27
 */
public class UserService {

    public List<User> listUser(List<Integer> userIdList) {
        System.out.println("【请求参数】：" + userIdList);
        List<User> userList = new ArrayList<>();
        for (Integer userId : userIdList) {
            User user = User.builder()
                    .id(userId)
                    .name("测试" + userId)
                    .build();
            userList.add(user);
        }

        return userList;
    }
}
