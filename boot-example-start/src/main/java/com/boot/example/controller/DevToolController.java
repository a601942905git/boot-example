package com.boot.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.controller.DevToolController
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午3:37
 */
@RestController
@RequestMapping("/dev/tool")
public class DevToolController {

    @RequestMapping("/")
    public String devTool() {
        return "devTool";
    }
}
