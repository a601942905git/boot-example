package com.boot.example.core.service;

import com.boot.example.core.model.User;

/**
 * com.boot.example.core.service.UserService
 *
 * @author lipeng
 * @date 2018/12/18 下午5:49
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @param username  用户名
     * @return          查询到的用户信息
     */
    User getUserByUsername(String username);

    /**
     * 根据用户编号查询用户信息
     * @param id    用户编号
     * @return      查询到的用户信息
     */
    User getUserById(Integer id);
}
