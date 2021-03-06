package com.boot.example.controller;

import com.boot.example.entity.Student;
import com.boot.example.request.UserListRequest;
import com.boot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.boot.example.controller.CacheController
 *
 * @author lipeng
 * @date 2021/6/2 11:42 AM
 */
@RestController
@RequestMapping("/students")
public class CacheController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public List<Student> list(@RequestBody UserListRequest request) {
        return userService.list(request);
    }
}
