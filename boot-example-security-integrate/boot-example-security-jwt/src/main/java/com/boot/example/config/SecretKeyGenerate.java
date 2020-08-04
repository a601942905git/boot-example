package com.boot.example.config;

import com.boot.example.jwt.constants.JwtConstants;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * com.boot.example.config.SecretKeyGenerate
 *
 * @author lipeng
 * @date 2018/12/19 下午4:01
 */
public class SecretKeyGenerate {

    public static SecretKey generateKey() {
        byte[] bytes = new byte[32];
        return new SecretKeySpec(bytes, JwtConstants.DEFAULT_SIGNATURE_ALGORITHM.getJcaName());
    }
}
