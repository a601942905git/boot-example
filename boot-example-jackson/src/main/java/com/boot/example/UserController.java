package com.boot.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * com.boot.example.UserController
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午3:07
 */
@RestController
public class UserController {

    @RequestMapping("/")
    public User index() {
        return User.builder()
                .id(10001)
                .name("test jackson")
                .localDate(LocalDate.now())
                .localDateTime(LocalDateTime.now())
                .build();
    }

    @RequestMapping("/student")
    public Student student() {
        return Student.builder()
                .id(10002)
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
