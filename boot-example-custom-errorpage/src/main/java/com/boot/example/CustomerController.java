package com.boot.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.CustomerController
 *
 * @author lipeng
 * @dateTime 2018/12/12 下午9:17
 */
@RestController
@RequestMapping("/custom")
public class CustomerController {

    @RequestMapping("/")
    public void index() {
        throw new RuntimeException("抛出异常。。。。。。");
    }
}
