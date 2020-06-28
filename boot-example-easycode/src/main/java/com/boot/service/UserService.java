package com.boot.service;

import com.boot.entity.User;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author lipeng
 * @since 2020-06-25 20:29:21
 */
public interface UserService {

    User getById(Integer id);

    List<User> list(User user);

    Long count(User user);

    int insert(User user);

    int batchInsert(List<User> userList);

    int update(User user);

    int batchUpdate(List<Integer> idList);
}