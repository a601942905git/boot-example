package com.boot.example.controller;

import com.boot.example.service.DebugService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.controller.DebugController
 *
 * @author lipeng
 * @date 2020/7/3 5:30 PM
 */
@RestController
public class DebugController {

    private DebugService debugService;

    public DebugController(DebugService debugService) {
        this.debugService = debugService;
    }

    @RequestMapping("/")
    public String debug() {
        return debugService.debug();
    }
}
