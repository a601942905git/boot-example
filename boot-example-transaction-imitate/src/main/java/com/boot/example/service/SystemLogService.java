package com.boot.example.service;

import com.boot.example.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.service.SystemLogService
 *
 * @author lipeng
 * @date 2019-08-23 10:29
 */
@Slf4j
@Service
@SystemLog
public class SystemLogService {

    public String log(String name) throws InterruptedException {
        log.info("执行业务方法");
        TimeUnit.SECONDS.sleep(1);
        return "hello " + name;
    }
}
