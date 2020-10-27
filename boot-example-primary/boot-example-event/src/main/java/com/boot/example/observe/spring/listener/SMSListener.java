package com.boot.example.observe.spring.listener;

import com.boot.example.observe.spring.User;
import com.boot.example.observe.spring.event.RegisterEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.observe.spring.listener.SMSListener
 *
 * @author lipeng
 * @date 2018/12/21 下午3:06
 */
@Component
public class SMSListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == RegisterEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        User user = (User) event.getSource();
        System.out.println("【用户" + user.getName() + "注册成功】：发送短信");
    }
}
