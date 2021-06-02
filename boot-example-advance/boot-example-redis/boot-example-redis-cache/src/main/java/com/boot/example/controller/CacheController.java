package com.boot.example.controller;

import com.boot.example.service.UserService;
import com.boot.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/list")
    public List<Student> list() {
        return userService.list();
    }
}
