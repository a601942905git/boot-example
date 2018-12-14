package com.boot.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.boot.example.UserController
 *
 * @author lipeng
 * @dateTime 2018/12/14 上午10:28
 */
@Controller
public class UserController {

    private UserService userService;

    @RequestMapping("/")
    public void index() {
        userService.hello("spring beans");
    }
}
