package com.boot.example;

import org.springframework.stereotype.Service;

/**
 * com.boot.example.UserService
 *
 * @author lipeng
 * @dateTime 2018/12/14 上午10:28
 */
@Service
public class UserService {

    public String hello(String str) {
        return "Hello：" + str;
    }
}
