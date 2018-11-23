package com.boot.example.controller.email;

import com.boot.example.core.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/send")
    public String send() {
        emailService.sendSimpleMail("601942905@qq.com", "测试邮件", "一封来自Spring Boot应用的测试邮件！！！");
        return "邮件发送成功！！！";
    }
}
