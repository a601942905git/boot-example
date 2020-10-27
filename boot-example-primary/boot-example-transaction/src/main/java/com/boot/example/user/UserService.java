package com.boot.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.boot.example.user.UserService
 *
 * @author lipeng
 * @date 2019-02-01 11:04
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int saveUser(User user) {
        return userMapper.saveUser(user);
    }
}
