package com.boot.example;

import com.boot.exampl.petstore.client.invoker.ApiClient;
import com.boot.example.petstore.client.api.UserControllerApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.PetStoreIntegrationConfig
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午1:30
 */
@Configuration
public class SwaggerClientConfig {

    @Bean
    public UserControllerApi userControllerApi() {
        return new UserControllerApi(apiClient());
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient().setBasePath("http://localhost:8080");
    }
}
