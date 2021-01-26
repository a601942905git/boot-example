package com.boot.example.factory;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.factory.CustomProxyFactoryBean
 * 1. 创建TigerProxyFactoryBean对象，继承ProxyFactoryBean
 * 2. 创建TigerAdvice对象实现MethodInterceptor接口
 * 3. TigerProxyFactoryBean中指定代理目标对象和通知名称
 * 4. 注入代理对象
 * 5. 调用代理对象方法
 *
 * @author lipeng
 * @date 2021/1/25 8:05 PM
 */
@Component
public class TigerProxyFactoryBean extends ProxyFactoryBean implements InitializingBean {

    private static final long serialVersionUID = -6168210900695514940L;

    @Override
    public void afterPropertiesSet() throws Exception {
        setTarget(BeanUtils.instantiateClass(Tiger.class));
        setInterceptorNames("tigerAdvice");
    }
}
