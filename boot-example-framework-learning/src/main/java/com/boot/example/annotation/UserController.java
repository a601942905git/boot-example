package com.boot.example.annotation;

import com.boot.example.util.WebContextUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * com.boot.example.annotation.UserController
 *
 * @author lipeng
 * @date 2019/12/31 下午1:34
 */
@RestController
public class UserController implements UserApi {
    @Override
    public void hello(String name) throws NoSuchMethodException {
        System.out.println(WebContextUtils.getRequest());
        Method method = this.getClass().getMethod("hello", String.class);
        ApiOperation apiOperation = AnnotatedElementUtils.findMergedAnnotation(method, ApiOperation.class);
        System.out.println(apiOperation.value());
    }
}
