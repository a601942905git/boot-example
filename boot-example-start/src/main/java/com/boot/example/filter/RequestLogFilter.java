package com.boot.example.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * com.boot.example.filter.RequestLogFilter
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午4:14
 */
@Component
@Order(2)
public class RequestLogFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("日志过滤器初始化。。。。。。");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println(request.getRequestURL());
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        System.out.println("日志过滤器销毁。。。。。。");
    }
}
