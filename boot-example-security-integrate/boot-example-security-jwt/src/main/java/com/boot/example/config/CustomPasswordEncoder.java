package com.boot.example.config;

import com.boot.example.util.MD5Utils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * com.boot.example.config.CustomPasswordEncoder
 *
 * @author lipeng
 * @date 2018/12/19 下午6:16
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return MD5Utils.encode(String.valueOf(rawPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Objects.equals(encode(rawPassword), encodedPassword);
    }
}
