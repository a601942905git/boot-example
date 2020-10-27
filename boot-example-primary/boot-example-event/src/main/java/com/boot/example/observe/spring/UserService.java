package com.boot.example.observe.spring;

import com.boot.example.observe.spring.publish.RegisterEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.observe.spring.UserService
 *
 * @author lipeng
 * @date 2018/12/21 下午4:17
 */
@Service
public class UserService {

    @Autowired
    private RegisterEventPublisher registerEventPublisher;

    public User register() {
        User user = User.builder().id(10001).name("张三").build();
        System.out.println("【用户" + user.getName() + "注册成功！】");
        registerEventPublisher.publishRegisterEvent(user);
        return user;
    }
}
