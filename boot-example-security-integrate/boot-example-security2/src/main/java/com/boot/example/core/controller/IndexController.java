package com.boot.example.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.boot.example.core.controller.IndexController
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午3:38
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
