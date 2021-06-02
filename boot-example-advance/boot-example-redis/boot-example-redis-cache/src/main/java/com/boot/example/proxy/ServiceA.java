package com.boot.example.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * com.boot.example.proxy.ServiceA
 *
 * @author lipeng
 * @date 2021/6/2 10:55 AM
 */
@Slf4j
public class ServiceA {

    private ServiceC serviceC;

    public ServiceA(ServiceC serviceC) {
        log.info("serviceC in serviceA construct：" + serviceC);
        this.serviceC = serviceC;
    }
}
