package com.boot.example.factory;

import com.boot.example.entity.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.factory.RpcFactoryBean
 *
 * @author lipeng
 * @date 2020/11/3 4:09 PM
 */
@Component
public class RpcFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
