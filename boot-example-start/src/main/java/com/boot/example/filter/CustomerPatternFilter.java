package com.boot.example.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * com.boot.example.filter.CustomerPatternFilter
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午4:30
 */
public class CustomerPatternFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("拦截特定url过滤器初始化。。。。。。");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("拦截特定url过滤器执行逻辑。。。。。。");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        System.out.println("拦截特定url过滤器资源清理。。。。。。");
    }
}
