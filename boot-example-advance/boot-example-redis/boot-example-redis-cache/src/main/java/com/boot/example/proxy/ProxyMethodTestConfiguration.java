package com.boot.example.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.proxy.ProxyMethodTestConfiguration
 *
 * proxyBeanMethods是否对@Bean注解标注的方法进行代理
 * true：进行代理，方法直接调用返回的是同一个对象
 * false：不进行代理，方法直接调用返回的是不同对象
 *
 * 1.proxyBeanMethods设置为true
 * serviceC in serviceA construct：com.boot.example.proxy.ServiceC@476fe690
 * serviceC in serviceB construct：com.boot.example.proxy.ServiceC@476fe690
 * 通过方法的直接调用，返回的是同一个对象
 *
 * 2.proxyBeanMethods设置为false
 *     2.1 方法直接调用
 *     serviceC in serviceA construct：com.boot.example.proxy.ServiceC@19f7222e
 *     serviceC in serviceB construct：com.boot.example.proxy.ServiceC@3f725306
 *     通过方法的直接调用，返回的是不同对象
 *
 *     2.2 通过注入方式
 *     serviceC in serviceA construct：com.boot.example.proxy.ServiceC@76563d26
 *     serviceC in serviceB construct：com.boot.example.proxy.ServiceC@76563d26
 *     通过注入方式，返回的是同一对象
 *
 * @author lipeng
 * @date 2021/6/2 10:55 AM
 */
@Configuration(proxyBeanMethods = false)
public class ProxyMethodTestConfiguration {

    @Bean
    ServiceA serviceA(ServiceC serviceC){
        return new ServiceA(serviceC);
    }

    @Bean
    ServiceB serviceB(ServiceC serviceC){
        return new ServiceB(serviceC);
    }

    @Bean
    ServiceC sharedService(){
        return new ServiceC();
    }
}
