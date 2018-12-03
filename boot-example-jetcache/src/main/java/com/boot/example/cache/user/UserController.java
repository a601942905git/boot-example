package com.boot.example.cache.user;

import com.boot.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

/**
 * com.boot.example.cache.user.UserController
 *
 * @author lipeng
 * @dateTime 2018/11/30 下午2:21
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return userService.getUser(id);
    }

    @RequestMapping("/getUserFromMethodCache")
    public User getUserFromMethodCache() {
        return userService.getUserFromMethodCache(10006);
    }

    @RequestMapping("/delete")
    public String deleteUser() {
        userService.remove(10006);
        return "成功删除用户信息";
    }

    @RequestMapping("/update")
    public String update() {
        User user = User.builder()
                .id(10006)
                .name("update cache")
                .build();
        userService.updateUser(user);
        return "成功修改用户信息";
    }

    @RequestMapping("/getUserFromRefreshCache")
    public User getUserFromRefreshCache() throws UnknownHostException {
        return userService.getUserFromRefreshCache();
    }
}
