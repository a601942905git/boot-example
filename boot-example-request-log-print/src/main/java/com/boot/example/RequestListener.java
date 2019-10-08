package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * com.boot.example.RequestListener
 *
 * @author lipeng
 * @date 2019/10/8 下午5:35
 */
@Component
@Slf4j
public class RequestListener implements ApplicationListener<ServletRequestHandledEvent> {

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        String requestUrl = event.getRequestUrl();
        String requestMethod = event.getMethod();
        Long processTime = event.getProcessingTimeMillis();
        log.info("requestUrl：{}，requestMethod：{}，processTime：{}ms", requestUrl, requestMethod, processTime);
    }
}
