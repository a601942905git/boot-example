package com.boot.example.observe.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.observe.spring.SpringEventApplication
 *
 * @author lipeng
 * @date 2018/12/21 下午4:18
 */
@SpringBootApplication
public class SpringEventApplication {

    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        SpringEventApplication.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringEventApplication.class, args);
        System.out.println(userService.register());
    }
}
