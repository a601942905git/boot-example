package com.boot.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * com.boot.example.filter.CustomerFilter1
 *
 * @author lipeng
 * @dateTime 2018/12/3 下午9:29
 */
@WebFilter(filterName = "customerFilter1", urlPatterns = "/customer/*")
public class CustomerFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
