package com.boot.example.observe.spring.event;

import com.boot.example.observe.spring.User;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * com.boot.example.observe.spring.event.RegisterEvent
 * 注册事件
 * @author lipeng
 * @date 2018/12/21 下午3:00
 */
public class RegisterEvent extends ApplicationEvent implements Serializable {

    private static final long serialVersionUID = -4753665584529412874L;

    public RegisterEvent(User user) {
        super(user);
    }
}
