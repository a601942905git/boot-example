package com.boot.example.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.endpoint.CustomEndpointAutoconfiguration
 *
 * @author lipeng
 * @date 2021/1/22 3:45 PM
 */
@Configuration
public class CustomEndpointAutoConfiguration {

    @Bean
    public CustomEndpoint customEndpoint() {
        return new CustomEndpoint();
    }
}
