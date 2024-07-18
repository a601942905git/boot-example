package com.boot.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * com.boot.example.DockerApplication
 *
 * @see EmbeddedWebServerFactoryCustomizerAutoConfiguration
 *
 * @author lipeng
 * @date 2020/6/30 9:57 AM
 */
@SpringBootApplication
@RestController
public class DockerApplication {


    private static final Logger log = LoggerFactory.getLogger(DockerApplication.class);

    @GetMapping("/")
    public String home() {
        try {
            log.info("current thread: {}", Thread.currentThread());
            return "Hello Docker World：" + InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "Hello Docker World";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }
}
