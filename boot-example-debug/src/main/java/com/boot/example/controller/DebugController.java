package com.boot.example.controller;

import com.boot.example.service.DebugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.controller.DebugController
 *
 * @author lipeng
 * @date 2020/7/3 5:30 PM
 */
@RestController
@Slf4j
public class DebugController {

    private DebugService debugService;

    public DebugController(DebugService debugService) {
        this.debugService = debugService;
    }

    @RequestMapping("/")
    public String debug() {
        return debugService.debug();
    }

    @GetMapping("/gracefulShutdown")
    public String gracefulShutdown() throws InterruptedException {
        TimeUnit.SECONDS.sleep(20);
        log.info("关闭应用，任务可以继续执行....");
        return "关闭应用，任务可以继续执行....";
    }
}
