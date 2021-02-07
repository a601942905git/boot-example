package com.boot.example.service;

import com.boot.example.entity.User;
import com.boot.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.service.UserService
 *
 * @author lipeng
 * @date 2020/12/23 10:23 AM
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> listUser() {
        List<User> userList = new ArrayList<>();
        List<User> userList1 = userMapper.list();
        List<User> userList2 = userMapper.list();
        userList.addAll(userList1);
        userList.addAll(userList2);
        return userList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        userMapper.save(user);
    }
}
