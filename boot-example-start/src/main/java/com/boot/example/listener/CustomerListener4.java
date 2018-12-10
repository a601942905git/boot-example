package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.boot.example.listener.CustomerListener2
 *
 * @author lipeng
 * @dateTime 2018/12/10 下午3:54
 */
@Slf4j
public class CustomerListener4 implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("【ApplicationReadyEvent execute】");
    }
}
