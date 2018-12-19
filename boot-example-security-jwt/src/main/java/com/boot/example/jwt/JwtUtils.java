package com.boot.example.jwt;

import com.boot.example.config.SecretKeyGenerate;
import com.boot.example.jwt.constants.JwtConstants;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;
import java.util.Map;

;

/**
 * com.boot.example.jwt.JwtUtils
 *
 * @author lipeng
 * @date 2018/12/19 上午9:48
 */
public class JwtUtils {

    /**
     * 加密使用的私钥
     */
    private static Key secretKey;

    static {
        secretKey = SecretKeyGenerate.generateKey();
    }

    /**
     * 验证token
     * @param token 需要校验的token
     * @return      校验结果，true：校验成功，false：校验失败
     */
    public static Jws<Claims> validateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (SignatureException exception) {
            return null;
        }
    }

    /**
     * 生成jwt token
     * @param id                    编号
     * @param issUser               签发者
     * @param subject               面向的用户
     * @param audience              接收方
     * @param issuedAt              签发时间
     * @param expiration            过期时间
     * @param signatureAlgorithm    签名算法
     * @param key                   签名密钥
     * @return                      生成的token
     */
    public static String generateToken(String id, String issUser, String subject,
                                       String audience, Date issuedAt, Date expiration,
                                       SignatureAlgorithm signatureAlgorithm, Key key) {
        return Jwts.builder()
                .setId(id)
                .setIssuer(issUser)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signatureAlgorithm, key)
                .compact();
    }

    /**
     * 生成jwt token
     * @param body  内容可以是如上方法,处理前面方式和签名密钥之外
     * @return      生成签名
     */
    public static String generateToken(Map<String, Object> body) {
        return Jwts.builder()
                .setClaims(body)
                .signWith(JwtConstants.DEFAULT_SIGNATURE_ALGORITHM, secretKey)
                .compact();
    }

    /**
     * 生成jwt token
     * @param header    header可以自定义参数,默认只有前面算法
     * @param body      内容可以是如上方法,处理前面方式和签名密钥之外
     * @return          生成的token
     */
    public static String generateToken(Map<String, Object> header, Map<String, Object> body) {
        SignatureAlgorithm signatureAlgorithm = (SignatureAlgorithm) header.get(JwsHeader.ALGORITHM);
        return Jwts.builder()
                .setHeader(header)
                .setClaims(body)
                .signWith(signatureAlgorithm, secretKey)
                .compact();
    }
}
