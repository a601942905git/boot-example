package com.boot.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.config.RestTemplateConfigure
 *
 * @author lipeng
 * @dateTime 2018/11/23 上午10:52
 */
@Configuration
public class RestTemplateConfigure {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
