package com.boot.example.listener;

import com.boot.example.entity.User;
import com.boot.example.event.RegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.listener.RegisterListener
 *
 * @author lipeng
 * @date 2020/11/3 11:01 AM
 */
@Component
@Slf4j
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    @Override
    public void onApplicationEvent(RegisterEvent event) {
        User user = (User) event.getSource();
        log.info("当前注册用户id：{}，用户名：{}", user.getId(), user.getName());
    }
}
