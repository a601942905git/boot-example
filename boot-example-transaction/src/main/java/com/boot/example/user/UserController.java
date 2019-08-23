package com.boot.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.user.UserController
 *
 * @author lipeng
 * @date 2019-08-13 11:51
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }
}
