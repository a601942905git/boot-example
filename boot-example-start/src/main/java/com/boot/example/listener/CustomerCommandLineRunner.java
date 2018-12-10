package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.listener.CustomerListener6
 *
 * @author lipeng
 * @dateTime 2018/12/10 下午3:57
 */
@Component
@Slf4j
public class CustomerCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("【CustomerCommandLineRunner execute】");
    }
}
