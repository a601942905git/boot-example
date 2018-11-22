package com.boot.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.boot.example.controller.IndexController
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午5:24
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", "home");
        return "index";
    }
}
