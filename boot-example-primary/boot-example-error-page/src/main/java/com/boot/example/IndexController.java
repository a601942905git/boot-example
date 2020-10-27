package com.boot.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.ErrorController
 *
 * @author lipeng
 * @date 2019/11/9 下午10:33
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public void index() {
        throw new NullPointerException();
    }
}
