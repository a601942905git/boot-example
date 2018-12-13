package com.boot.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.GreeterConfigure
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午6:12
 */
@Configuration
public class GreeterConfigure {

    @Bean
    public GreeterConfig greeterConfig() {
        GreeterConfig greeterConfig = new GreeterConfig();

        greeterConfig.setUserName("smile");
        greeterConfig.setMorningMessage("good morning");
        greeterConfig.setAfternoonMessage("good afternoon");
        greeterConfig.setEveningMessage("good evening");
        greeterConfig.setNightMessage("good night");
        return greeterConfig;
    }
}
