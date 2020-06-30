package com.boot.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.controller.DcokerController
 *
 * @author lipeng
 * @date 2020/6/30 9:58 AM
 */
@RestController
public class DockerController {

    @GetMapping("/")
    public String hello() {
        return "this is a docker application";
    }
}
