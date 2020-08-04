package com.boot.example.jwt.constants;

import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Clock;

/**
 * com.boot.example.jwt.constants.JwtConstants
 *
 * @author lipeng
 * @date 2018/12/19 上午9:46
 */
public interface JwtConstants {

    /**
     * 加密算法
     */
    SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * 默认的签发时间
     */
    Long DEFAULT_ISSUED_AT = Clock.systemDefaultZone().millis();

    /**
     *  默认时长30天
     */
    Long DEFAULT_TIME = 30L * 24 * 60 * 60 * 1000;

    /**
     * 默认失效时间
     */
    Long DEFAULT_EXPIRATION_AT = Clock.systemDefaultZone().millis() + DEFAULT_TIME;
}
