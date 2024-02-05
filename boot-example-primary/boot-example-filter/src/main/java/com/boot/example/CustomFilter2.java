package com.boot.example;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * com.boot.example.CustomFilter2
 *
 * @author lipeng
 * @date 2019/10/27 下午8:38
 */
@Slf4j
public class CustomFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("执行CustomFilter2的doFilter方法");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("执行CustomFilter2的init方法");
    }

    @Override
    public void destroy() {
        log.info("执行CustomFilter2的destroy方法");
    }
}
