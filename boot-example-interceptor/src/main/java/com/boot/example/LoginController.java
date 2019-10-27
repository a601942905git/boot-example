package com.boot.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * com.boot.example.LoginController
 *
 * @author lipeng
 * @date 2019/10/27 下午11:11
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/")
    public String login() {
        return "获取到的token为：" + UUID.randomUUID();
    }
}
