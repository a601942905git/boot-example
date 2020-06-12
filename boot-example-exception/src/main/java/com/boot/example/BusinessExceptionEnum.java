package com.boot.example;

/**
 * com.boot.example.BusinessExceptionEnum
 *
 * @author lipeng
 * @date 2020/6/5 2:14 PM
 */
public enum BusinessExceptionEnum implements Asserts {

    USER_NOT_FOUND(10001, "user not found"),
    ORDER_NOT_FOUND(10002, "order not found"),
    ;


    private Integer code;

    private String message;

    BusinessExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public BusinessException newException() {
        return new BusinessException(this);
    }
}
