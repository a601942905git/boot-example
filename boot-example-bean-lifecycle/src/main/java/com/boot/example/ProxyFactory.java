package com.boot.example;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * com.boot.example.ProxyFactory
 *
 * @author lipeng
 * @date 2019-03-20 10:41
 */
public class ProxyFactory implements MethodInterceptor {

    public Object object;

    public ProxyFactory(Object object) {
        this.object = object;
    }

    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("【被代理方法前置增强】");
        Object result = method.invoke(object, args);
        System.out.println("【被代理方法后置增强】");
        return result;
    }
}
