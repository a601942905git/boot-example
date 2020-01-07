package com.boot.example.annotation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.boot.example.annotation.UserApi
 *
 * @author lipeng
 * @date 2019/12/31 下午1:33
 */
@RequestMapping("/user")
@Api("用户中心接口")
public interface UserApi {

    @RequestMapping("/hello")
    @ApiOperation(value = "打招呼")
    void hello(String name) throws NoSuchMethodException;
}
