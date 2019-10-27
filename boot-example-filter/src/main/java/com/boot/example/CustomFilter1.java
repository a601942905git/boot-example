package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
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
