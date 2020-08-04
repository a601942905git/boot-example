package com.boot.example.core.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
    public String error(HttpServletRequest request, Model model) {
        String errorMessage = "";
        Object exceptionObj = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exceptionObj instanceof AuthenticationException) {
            errorMessage = ((AuthenticationException) exceptionObj).getMessage();
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}
