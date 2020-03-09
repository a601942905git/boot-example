package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment environment;

    @GetMapping("/")
    public String index() {
        String port = environment.getProperty("server.port");
        return "hello spring boot from " + port;
    }
}
