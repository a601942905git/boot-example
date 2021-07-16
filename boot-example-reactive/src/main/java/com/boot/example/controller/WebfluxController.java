package com.boot.example.controller;

import com.boot.example.entity.Student;
import com.boot.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * com.boot.example.controller.WebfluxController
 *
 *
 * @author lipeng
 * @date 2021/7/14 2:04 PM
 */
@RestController
@RequestMapping("/students")
public class WebfluxController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public Mono<Student> get() {
        return Mono.just(studentService.get());
    }

    @GetMapping("/list")
    public Flux<Student> list() {
        return Flux.fromIterable(studentService.list());
    }
}
