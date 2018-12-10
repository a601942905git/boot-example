package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.boot.example.listener.CustomerListener2
 * context和
 * @author lipeng
 * @dateTime 2018/12/10 下午3:54
 */
@Slf4j
public class CustomerListener3 implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("【ApplicationStartedEvent execute】");
    }
}
