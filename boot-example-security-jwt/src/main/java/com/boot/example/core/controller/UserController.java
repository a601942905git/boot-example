package com.boot.example.core.controller;

import com.boot.example.config.SubjectHolder;
import com.boot.example.core.model.User;
import com.boot.example.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.core.controller.UserController
 *
 * @author lipeng
 * @date 2018/12/19 上午11:02
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        System.out.println("【控制器层获取用户编号：】" + SubjectHolder.get());
        return userService.getUserById(id);
    }

}
