package com.boot.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.IndexController
 *
 * @author lipeng
 * @date 2020/3/2 上午11:17
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "hello spring boot";
    }
}
