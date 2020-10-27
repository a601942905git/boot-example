package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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
