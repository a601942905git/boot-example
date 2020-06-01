package com.boot.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.ShutDownController
 *
 * @author lipeng
 * @date 2020/6/1 11:15 AM
 */
@RestController
public class ShutDownController {

    @RequestMapping("shut/down")
    public String shutDown() throws InterruptedException {
        TimeUnit.SECONDS.sleep(20);
        return "hello";
    }
}
