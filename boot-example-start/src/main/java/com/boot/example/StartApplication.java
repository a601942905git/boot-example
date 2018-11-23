package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * com.boot.example.StartApplication
 *
 * @author lipeng
 * @dateTime 2018/11/21 下午7:27
 */
@SpringBootApplication
@MapperScan(basePackages = "com.boot.example")
@Slf4j
public class StartApplication {

    public static void main(String[] args) throws UnknownHostException {
        Environment environment = SpringApplication.run(StartApplication.class, args).getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        log.info("=================================");
        log.info("      项目名称:" + environment.getProperty("spring.application.name"));
        log.info("      项目访问地址:" + ip + ":" + environment.getProperty("server.port"));
        log.info("=================================");
    }
}
