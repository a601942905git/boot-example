package com.boot.example.factory;

import com.boot.example.entity.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.factory.UserFactoryBean
 *
 * @author lipeng
 * @date 2021/1/26 1:28 PM
 */

@Component
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}