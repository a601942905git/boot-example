package com.boot.example.config;

import com.boot.example.BaseResponse;
import com.boot.example.constants.AuthorizationConstants;
import com.boot.example.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

/**
 * com.boot.example.config.AuthenticationResultUtils
 *
 * @author lipeng
 * @date 2018/12/19 下午3:41
 */
public class AuthenticationResultUtils {

    /**
     * 认证成功之后生成token
     * 将用户的编号放入token
     * 以后每次请求，服务器端可以自己从解析的token中获取用户编号
     * @param response      响应对象
     * @param authResult    认证结果对象
     * @throws IOException  io异常
     */
    public static void success(HttpServletResponse response, Authentication authResult) throws IOException {
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", authResult.getName());
        map.put("iss", "Spring Boot Security Jwt");
        map.put("sub", authResult.getName());
        map.put("aud", "天朝应用");
        map.put("iat", Clock.systemDefaultZone().millis());
        map.put("exp", Clock.systemDefaultZone().millis() + 10 * 24 * 60 * 60 * 1000);

        // 生成token
        String token = JwtUtils.generateToken(map);
        // 响应token
        response.addHeader(AuthorizationConstants.AUTHORIZATION_HEADER_KEY,
                AuthorizationConstants.AUTHORIZATION_HEADER_VALUE_PREFIX + token);

        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = BaseResponse.error(Result.ResultCodeEnum.LOGIN_AUTHENTICATION_SUCCESS.getCode(),
                Result.ResultMessageEnum.LOGIN_AUTHENTICATION_SUCCESS.getMessage());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }

    public static void fail(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = BaseResponse.error(Result.ResultCodeEnum.LOGIN_AUTHENTICATION_FAIL.getCode(),
                Result.ResultMessageEnum.LOGIN_AUTHENTICATION_FAIL.getMessage());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}
