package com.boot.example;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * com.boot.example.UserController
 *
 * @author lipeng
 * @dateTime 2018/12/13 上午11:25
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @ApiOperation(value = "查询用户详情信息", notes = "根据用户编号查询用户详情信息")
    @GetMapping("/{id}")
    public User getUser(@PathVariable(name = "id") Integer id) {
        return User.builder()
                .id(id)
                .name("测试swagger api")
                .build();
    }

    @ApiOperation(value = "新增用户", notes = "新增用户信息")
    @PostMapping("/")
    public void saveUser(@RequestBody User user) {

    }

    @ApiOperation(value = "修改用户", notes = "修改用户信息")
    @PutMapping("/")
    public void modifyUser(@RequestBody User user) {

    }

    @ApiOperation(value = "删除用户", notes = "根据用户编号删除用户信息")
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable(name = "id") Integer id) {

    }
}
