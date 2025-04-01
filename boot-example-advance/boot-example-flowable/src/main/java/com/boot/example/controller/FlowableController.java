package com.boot.example.controller;

import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lipeng
 * &#064;date 2025/4/1 16:20:12
 */
@RestController
@RequestMapping("/flowable")
public class FlowableController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/test")
    public void test() {
        runtimeService.startProcessInstanceByKey("serviceTaskExample");
    }
}
