package com.boot.example.listener;

import com.boot.example.util.ApplicationContextUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.lisenter.ApplicationStartedListener
 *
 * @author lipeng
 * @date 2020/1/6 下午6:53
 */
@Component
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }
}
