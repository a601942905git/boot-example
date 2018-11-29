package com.boot.example;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * com.boot.example.SpringBootAdminServerApplication
 *
 * @author lipeng
 * @dateTime 2018/11/28 下午6:23
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@Slf4j
public class SpringBootAdminServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        Environment environment = SpringApplication.run(SpringBootAdminServerApplication.class, args).getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        log.info("=================================");
        log.info("      项目名称:" + environment.getProperty("spring.application.name"));
        log.info("      项目访问地址:" + ip + ":" + environment.getProperty("server.port"));
        log.info("=================================");
    }
}
