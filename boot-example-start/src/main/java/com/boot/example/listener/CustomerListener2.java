package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.boot.example.listener.CustomerListener2
 * 上下文已初始化好，bean还未被初始化
 * @author lipeng
 * @dateTime 2018/12/10 下午3:54
 */
@Slf4j
public class CustomerListener2 implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        log.info("【ApplicationPreparedEvent execute】");
    }
}
