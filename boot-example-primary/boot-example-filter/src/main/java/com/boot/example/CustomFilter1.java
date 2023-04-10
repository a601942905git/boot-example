package com.boot.example;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.boot.example.CustomFilter1
 *
 * @author lipeng
 * @date 2019/10/27 下午8:36
 */
@Component
@Order(1)
@Slf4j
public class CustomFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("执行被@Component标注的CustomFilter1的doFilter方法");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("执行被@Component标注的CustomFilter1的init方法");
    }

    @Override
    public void destroy() {
        log.info("执行被@Component标注的CustomFilter1的destroy方法");
    }
}
