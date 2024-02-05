package com.boot.example.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.boot.example.config.CustomAuthenticationEntryPoint
 * 如果客户端请求了必须携带token的url，但是却没有token
 * 自定义返回错误信息，让前端捕获到，从而跳到登录界面
 * @author lipeng
 * @date 2018/12/21 上午11:44
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AuthenticationResultUtils.fail(response);
    }
}
