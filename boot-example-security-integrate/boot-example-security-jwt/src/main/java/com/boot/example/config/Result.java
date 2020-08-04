package com.boot.example.config;

/**
 * com.boot.example.config.Result
 *
 * @author lipeng
 * @date 2018/12/19 下午1:46
 */
public class Result {

    /**
     * 响应结果码
     */
    public enum ResultCodeEnum {
        // 成功
        SUCCESS("10001"),

        // 失败
        FAIL("-10001"),

        // 登录认证成功
        LOGIN_AUTHENTICATION_SUCCESS("10002"),

        // 登录认证失败
        LOGIN_AUTHENTICATION_FAIL("-10002");

        /**
         * 结果码
         */
        private String code;

        ResultCodeEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    /**
     * 响应结果消息
     */
    public enum ResultMessageEnum {
        // 成功
        SUCCESS("响应成功"),

        // 失败
        FAIL("响应失败"),

        // 登录认证成功
        LOGIN_AUTHENTICATION_SUCCESS("认证成功"),

        // 登录认证失败
        LOGIN_AUTHENTICATION_FAIL("认证失败");

        private String message;

        ResultMessageEnum(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
