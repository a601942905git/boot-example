package com.boot.example;

import com.boot.example.config.Result;
import lombok.*;

/**
 * com.boot.example.BaseResponse
 * 响应对象
 * @author lipeng
 * @date 2018/12/19 下午1:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResponse <T>{

    private String code;

    private String message;

    private T result;

    public static <T> BaseResponse<T> success() {
        return success(null);
    }

    public static <T> BaseResponse<T> success(T result) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(Result.ResultCodeEnum.SUCCESS.getCode());
        baseResponse.setMessage(Result.ResultMessageEnum.SUCCESS.getMessage());
        baseResponse.setResult(result);
        return baseResponse;
    }

    public static <T> BaseResponse error() {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(Result.ResultCodeEnum.FAIL.getCode());
        baseResponse.setMessage(Result.ResultMessageEnum.FAIL.getMessage());
        return baseResponse;
    }

    public static <T> BaseResponse error(String code, String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        return baseResponse;
    }
}
