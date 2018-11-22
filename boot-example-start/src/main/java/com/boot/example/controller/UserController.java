package com.boot.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.controller.UserController
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午4:44
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/")
    public String index() {
        System.out.println("/users/开始执行");
        return "hello users";
    }
}
