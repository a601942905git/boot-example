package com.boot.example.core.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.core.controller.IndexController
 *
 * @author lipeng
 * @date 2018/12/19 上午11:14
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        throw new BadCredentialsException("认证失败");
    }
}
