package com.boot.example.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.annotation.SentinelAnnotationController
 *
 * @author lipeng
 * @dateTime 2018/12/5 上午10:21
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelAnnotationController {

    @Autowired
    private SentinelAnnotationService sentinelAnnotationService;

    @GetMapping("/")
    public String hello() {
        return sentinelAnnotationService.hello("sentinelAnnotation");
    }
}
