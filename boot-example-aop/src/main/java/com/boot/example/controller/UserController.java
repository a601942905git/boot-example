package com.boot.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.controller.UserController
 *
 * @author lipeng
 * @date 2018/12/26 上午10:03
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/")
    public String listUser() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "访问用户列表信息";
    }

    @RequestMapping("/save")
    public String saveUser() {
        return "新增用户信息";
    }

    @RequestMapping("/modify")
    public String modifyUser() {
        return "修改用户信息";
    }

    @RequestMapping("/delete")
    public String deleteUser() {
        throw new RuntimeException("删除用户失败");
    }
}
