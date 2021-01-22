package com.boot.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * com.boot.example.SwaggerConfigure
 *
 * @author lipeng
 * @dateTime 2018/12/13 上午10:35
 */
@EnableSwagger2
@Configuration
public class SwaggerConfigure {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.example"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("the api document about boot-example-swagger")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact(new Contact("pengli", "https://github.com/a601942905git/boot-example", "a601942905@163.com"))
                .version("1.0")
                .build();
    }
}
