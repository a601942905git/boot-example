package com.boot.example.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.security.WelcomeController
 *
 * @author lipeng
 * @dateTime 2018/12/14 上午11:23
 */
@RestController
public class WelcomeController {

    @RequestMapping("/")
    public String welcome() {
        return "登录成功，欢迎来到：https://a601942905git.github.io/";
    }
}
