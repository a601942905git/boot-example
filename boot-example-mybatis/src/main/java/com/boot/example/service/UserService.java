package com.boot.example.service;

import com.boot.example.entity.User;
import com.boot.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.boot.example.service.UserService
 *
 * @author lipeng
 * @date 2019/11/9 下午9:52
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户列表信息
     * @return 用户列表信息
     */
    public List<User> list() {
        return userMapper.list();
    }

    /**
     * 根据编号查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    /**
     * 新增用户
     * @param user 用户信息
     * @return 受影响的行数
     */
    public int save(User user) {
        return userMapper.save(user);
    }

    /**
     * 修改用户
     * @param user 用户信息
     * @return 受影响的行数
     */
    public int update(User user) {
        return userMapper.update(user);
    }
}
