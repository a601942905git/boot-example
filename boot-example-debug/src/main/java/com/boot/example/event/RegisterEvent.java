package com.boot.example.event;

import com.boot.example.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * com.boot.example.event.RegisterEvent
 *
 * @author lipeng
 * @date 2020/11/3 10:47 AM
 */
public class RegisterEvent extends ApplicationEvent {

    public RegisterEvent(User user) {
        super(user);
    }
}
