package com.boot.example.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.boot.example.core.controller.LoginController
 *
 * @author lipeng
 * @dateTime 2018/12/17 上午10:37
 */
@RequestMapping("/login")
@Controller
public class LoginController {

    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
