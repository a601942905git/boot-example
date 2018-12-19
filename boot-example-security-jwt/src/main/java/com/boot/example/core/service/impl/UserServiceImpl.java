package com.boot.example.core.service.impl;

import com.boot.example.config.SubjectHolder;
import com.boot.example.core.model.User;
import com.boot.example.core.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * com.boot.example.core.service.impl.UserServiceImpl
 *
 * @author lipeng
 * @date 2018/12/18 下午5:50
 */
@Service
public class UserServiceImpl implements UserService {
    private List<User> userList = new ArrayList<>();

    {
        initUserList();
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userList.stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
        return optionalUser.orElseGet(User::new);
    }

    @Override
    public User getUserById(Integer id) {
        System.out.println("【业务层获取用户编号：】" + SubjectHolder.get());
        Optional<User> optionalUser = userList.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
        return optionalUser.orElseGet(User::new);
    }

    private void initUserList() {
        User user1 = User.builder()
                .id(10001)
                .username("13260803861")
                .password("$2a$10$nLedQMtNIDiVxnGyr/D9yOXcMqX3RVljeYUHjxkOdiGkKZgFhi612")
                .build();

        User user2 = User.builder()
                .id(10002)
                .username("13260803862")
                .password("$2a$10$nLedQMtNIDiVxnGyr/D9yOXcMqX3RVljeYUHjxkOdiGkKZgFhi612")
                .build();

        User user3 = User.builder()
                .id(10003)
                .username("13260803863")
                .password("$2a$10$nLedQMtNIDiVxnGyr/D9yOXcMqX3RVljeYUHjxkOdiGkKZgFhi612")
                .build();

        User user4 = User.builder()
                .id(10004)
                .username("13260803864")
                .password("$2a$10$nLedQMtNIDiVxnGyr/D9yOXcMqX3RVljeYUHjxkOdiGkKZgFhi612")
                .build();

        User user5 = User.builder()
                .id(10005)
                .username("13260803865")
                .password("$2a$10$nLedQMtNIDiVxnGyr/D9yOXcMqX3RVljeYUHjxkOdiGkKZgFhi612")
                .build();

        userList.addAll(Arrays.asList(user1, user2, user3, user4, user5));
    }
}
