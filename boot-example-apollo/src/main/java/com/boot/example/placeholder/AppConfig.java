package com.boot.example.placeholder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.AppConfig
 *
 * @author lipeng
 * @dateTime 2018/12/10 上午9:54
 */
@Configuration
public class AppConfig {

    @Bean
    public TestJavaConfigBean testJavaConfigBean() {
        return new TestJavaConfigBean();
    }
}
