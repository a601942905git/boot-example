package com.boot.example.config;

import com.boot.example.constants.AuthorizationConstants;
import com.boot.example.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * com.boot.example.config.JwtAuthenticationFilter
 *
 * @author lipeng
 * @date 2018/12/19 上午10:49
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(AuthorizationConstants.AUTHORIZATION_HEADER_KEY);

        if (header == null || !header.startsWith(AuthorizationConstants.AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        // token认证失败
        if (Objects.isNull(authentication)) {
            AuthenticationResultUtils.fail(response);
        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
            SubjectHolder.remove();
        }
    }

    /**
     * 验证token，如果验证通过，将sub中的内容放入ThreadLocal中
     * @param request   请求对象
     * @return          UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AuthorizationConstants.AUTHORIZATION_HEADER_KEY);
        token = token.replace(AuthorizationConstants.AUTHORIZATION_HEADER_VALUE_PREFIX, "");
        Jws<Claims> jws = JwtUtils.validateToken(token);
        if  (Objects.isNull(jws)) {
            return null;
        } else {
            Claims body = jws.getBody();
            SubjectHolder.set(body.getSubject());
            return new UsernamePasswordAuthenticationToken(null, null, new ArrayList<>());
        }
    }
}
