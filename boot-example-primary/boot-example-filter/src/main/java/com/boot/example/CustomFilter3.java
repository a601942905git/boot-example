package com.boot.example;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.io.IOException;

/**
 * com.boot.example.CustomFilter3
 *
 * @author lipeng
 * @date 2019/10/27 下午8:39
 */
@Slf4j
@WebFilter(filterName = "customFilter3", urlPatterns = "/*")
@Order(3)
public class CustomFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("执行CustomFilter3的doFilter方法");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("执行CustomFilter3的init方法");
    }

    @Override
    public void destroy() {
        log.info("执行CustomFilter3的destroy方法");
    }
}
