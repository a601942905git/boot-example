package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * com.boot.example.listener.CustomerListener7
 *
 * @author lipeng
 * @dateTime 2018/12/10 下午3:59
 */
@Slf4j
public class CustomerListener5 implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("【ServletContextListener的contextInitialized execute】");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("【ServletContextListener的contextDestroyed execute】");
    }
}
