package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.controller.email.EmailController
 *
 * @author lipeng
 * @dateTime 2018/11/23 上午11:42
 */
@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String send() {
        emailService.sendSimpleMail("xxxx@163.com", "测试邮件", "一封来自Spring Boot应用的测试邮件！！！");
        return "邮件发送成功！！！";
    }
}
