package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.core.service.email.EmailService
 *
 * @author lipeng
 * @dateTime 2018/11/23 上午11:43
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private MailProperties mailProperties;

    /**
     * 发送纯文本的简单邮件
     * @param to 发送目标
     * @param subject 发送主题
     * @param content 发送内容
     */
    public void sendSimpleMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            sender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
    }
}
