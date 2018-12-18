package com.boot.example.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * com.boot.example.message.MessageConfigure
 *
 * @author lipeng
 * @date 2018/12/18 下午2:59
 */
@Configuration
public class MessageConfigure {

    /**
     * 设置国际化资源文件的basename为message
     * 并且设置编码为UTF-8
     * @return MessageSource
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
