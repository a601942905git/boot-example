package com.boot.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.UserController
 *
 * @author lipeng
 * @date 2019/10/27 下午11:12
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/")
    public String list() {
        return "user list page";
    }
}
