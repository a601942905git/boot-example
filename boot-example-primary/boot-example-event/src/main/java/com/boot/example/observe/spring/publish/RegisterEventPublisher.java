package com.boot.example.observe.spring.publish;

import com.boot.example.observe.spring.User;
import com.boot.example.observe.spring.event.RegisterEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.observe.spring.publish.RegisterPublisher
 * 注册事件发布者
 * @author lipeng
 * @date 2018/12/21 下午4:08
 */
@Component
public class RegisterEventPublisher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void publishRegisterEvent(User user) {
        applicationContext.publishEvent(new RegisterEvent(user));
    }
}
