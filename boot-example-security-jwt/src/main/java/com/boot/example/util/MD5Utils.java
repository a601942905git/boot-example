package com.boot.example.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * com.boot.example.util.MD5Utils
 *
 * @author lipeng
 * @date 2018/12/19 下午6:13
 */
public class MD5Utils {

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return encode结果
     */
    public static String encode(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes(Charset.defaultCharset()));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}
