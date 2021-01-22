package com.boot.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.SwaggerController
 *
 * @author lipeng
 * @dateTime 2018/12/13 上午10:50
 */
@RestController
public class SwaggerController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
