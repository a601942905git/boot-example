package com.boot.example;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.HelloController
 *
 * @author lipeng
 * @date 2018/12/24 下午1:51
 */
@RestController
public class HelloController {

    private Counter helloCounter = Metrics.counter("request_total", "uri", "/hello");

    private Counter hiCounter = Metrics.counter("request_total", "uri", "/hi");

    @RequestMapping("/hello")
    public void hello() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(5);
            helloCounter.increment();
        }
    }

    @RequestMapping("/hi")
    public void hi() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(10);
            hiCounter.increment();
        }
    }
}
