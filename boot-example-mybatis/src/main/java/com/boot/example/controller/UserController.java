package com.boot.example.controller;

import com.boot.example.entity.User;
import com.boot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.boot.example.UserController
 *
 * @author lipeng
 * @date 2019/11/9 下午9:47
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户列表信息
     * @return 用户列表信息
     */
    @GetMapping("/")
    public List<User> list() {
        return userService.list();
    }

    /**
     * 根据编号查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/id/{id}")
    public User getById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    /**
     * 新增用户
     * @param user 用户信息
     * @return 受影响的行数
     */
    @PostMapping("/")
    public int save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 修改用户
     * @param user 用户信息
     * @return 受影响的行数
     */
    @PutMapping("/")
    public int update(@RequestBody User user) {
        return userService.update(user);
    }
}
