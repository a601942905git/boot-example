package com.boot.example.config;

import com.boot.example.core.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * com.boot.example.config.JwtLoginFilter
 * jwt登录
 * @author lipeng
 * @date 2018/12/19 上午9:38
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String POST_METHOD = "POST";

    private AuthenticationManager authenticationManager;

    JwtLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        this.authenticationManager = authenticationManager;
    }

    /**
     *
     * @param request                   请求对象
     * @param response                  响应对象
     * @return                          认证对象
     * @throws AuthenticationException  认证异常
     * @throws IOException              io异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException {
        if (!request.getMethod().equals(POST_METHOD)) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(request.getInputStream(), User.class);

        String username = user.getUsername();
        String password = user.getPassword();

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        password = username + password;
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    /**
     * 认证失败
     * @param request       请求对象
     * @param response      响应对象
     * @param exception     认证异常
     * @throws IOException  io异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException exception) throws IOException {
        AuthenticationResultUtils.fail(response);
    }

    /**
     * 认证成功
     * @param request       请求对象
     * @param response      响应对象
     * @param chain         过滤器链
     * @param authResult    认证结w果对象
     * @throws IOException  io异常
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        AuthenticationResultUtils.success(response, authResult);
    }
}
