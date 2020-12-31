package com.boot.example.controller;

import com.boot.example.entity.User;
import com.boot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.boot.example.controller.UserController
 *
 * @author lipeng
 * @date 2020/12/23 10:21 AM
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> listUser() {
        return userService.listUser();
    }

    @PostMapping("/")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }
}
