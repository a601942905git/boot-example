package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

/**
 * com.boot.example.InternelMessageController
 *
 * @author lipeng
 * @dateTime 2018/12/12 上午11:17
 */
@Controller
public class InternelMessageController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/")
    public String index() {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(messageSource.getMessage("say", null, locale));
        return "index";
    }
}
