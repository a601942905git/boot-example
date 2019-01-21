package com.boot.example.outputjson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.outputjson.RestConfig
 *
 * @author lipeng
 * @date 2019-01-17 15:52
 */
@Configuration
public class RestConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
