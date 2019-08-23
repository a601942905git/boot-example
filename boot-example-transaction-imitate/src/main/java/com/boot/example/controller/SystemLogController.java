package com.boot.example.controller;

import com.boot.example.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.controller.SystemLogController
 *
 * @author lipeng
 * @date 2019-08-23 10:28
 */
@RestController
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    @GetMapping("/log")
    public String hello(@RequestParam("name") String name) throws InterruptedException {
        return systemLogService.log(name);
    }
}
