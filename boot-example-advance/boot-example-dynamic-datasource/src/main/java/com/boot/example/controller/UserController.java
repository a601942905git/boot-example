package com.boot.example.controller;

import com.boot.example.datasource.DynamicDataSourceConstants;
import com.boot.example.entity.User;
import com.boot.example.request.UserListRequest;
import com.boot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.controller.UserController
 *
 * @author lipeng
 * @date 2021/2/4 10:18 AM
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public List<User> listUser(@RequestBody UserListRequest request) {
        if (Objects.equals(request.getDynamicDataSourceKey(), DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_MASTER)) {
            return userService.listMasterUser();
        } else if (Objects.equals(request.getDynamicDataSourceKey(), DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_SLAVE)) {
            return userService.listSlaveUser();
        } else {
            return userService.listUser();
        }
    }
}
