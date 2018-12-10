package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * com.boot.example.listener.CustomerListener1
 *
 * @author lipeng
 * @dateTime 2018/12/10 下午3:43
 */
@Slf4j
public class CustomerListener1 implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            log.info("【ContextRefreshedEvent execute】");
        }
    }
}
