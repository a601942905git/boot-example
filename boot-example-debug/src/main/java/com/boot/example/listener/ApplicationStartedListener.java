package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.lisenter.ApplicationStartedListener
 *
 * @author lipeng
 * @date 2020/1/6 下午6:53
 */
@Component
@Slf4j
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("spring application started......");
    }
}
