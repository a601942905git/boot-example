package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.DockerApplication
 *
 * @author lipeng
 * @date 2020/6/30 9:57 AM
 */
@SpringBootApplication
@RestController
public class DockerApplication {


    @GetMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }
}
