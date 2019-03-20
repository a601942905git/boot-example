package com.boot.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.UserBeanPostProcessor
 *
 * @author lipeng
 * @date 2019-03-20 10:31
 */
@Component
public class CustomerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == User.class) {
            User user = (User) bean;
            System.out.println("【User】：" + user.getId());
            System.out.println("【调用postProcessBeforeInitialization】");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == User.class) {
            ProxyFactory proxyFactory = new ProxyFactory(bean);
            bean = proxyFactory.getProxy();
            System.out.println("【调用postProcessAfterInitialization】");
            return bean;
        }
        return bean;
    }
}
