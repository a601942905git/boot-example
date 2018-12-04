package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * com.boot.example.SentryApplication
 *
 * @author lipeng
 * @dateTime 2018/12/4 上午11:37
 */
@Controller
@SpringBootApplication
public class SentryApplication {

    /**
     * Register a HandlerExceptionResolver that sends all exceptions to Sentry
     *     and then defers all handling to the other HandlerExceptionResolvers.
     *     You should only register this is you are not using a logging integration,
     *     otherwise you may double report exceptions.
     * @return
     */
    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver();
    }

    /**
     * Register a ServletContextInitializer that installs the SentryServletRequestListener
     *     so that Sentry events contain HTTP request information.
     *     This should only be necessary in Spring Boot applications. "Classic" Spring
     *     should automatically load the `io.sentry.servlet.SentryServletContainerInitializer`.
     * @return
     */
    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new io.sentry.spring.SentryServletContextInitializer();
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        int x = 1 / 0;

        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SentryApplication.class, args);
    }
}
