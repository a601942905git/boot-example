package com.boot.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.IndexController
 *
 * @author lipeng
 * @date 2019/11/2 上午9:29
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "hello interceptor";
    }
}
