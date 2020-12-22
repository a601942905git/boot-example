package com.boot.example.listener;

import com.boot.example.util.ApplicationContextUtils;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.boot.example.lisenter.ApplicationStartedListener
 *
 * @author lipeng
 * @date 2020/1/6 下午6:53
 */
public class ApplicationContextInitializedEventListener implements ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        ApplicationContextUtils.setApplicationContext(event.getApplicationContext());
    }
}
