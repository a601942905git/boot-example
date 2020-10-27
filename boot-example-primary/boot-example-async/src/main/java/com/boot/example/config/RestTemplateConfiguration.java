package com.boot.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.config.RestTemplateConfiguration
 *
 * @author lipeng
 * @date 2018/12/24 上午10:40
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
