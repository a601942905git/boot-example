package com.boot.example.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * com.boot.example.filter.CustomerFilter
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午3:58
 */
@Component
@Order(1)
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("自定义过滤器初始化。。。。。。");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("自定义过滤器逻辑处理");
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        System.out.println("自定义过滤器资源清理。。。。。。");
    }
}
