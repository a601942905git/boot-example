package com.boot.example.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * com.boot.example.proxy.ServiceB
 *
 * @author lipeng
 * @date 2021/6/2 10:55 AM
 */
@Slf4j
public class ServiceB {

    private ServiceC serviceC;

    public ServiceB(ServiceC serviceC) {
        log.info("serviceC in serviceB construct：" + serviceC);
        this.serviceC = serviceC;
    }
}
