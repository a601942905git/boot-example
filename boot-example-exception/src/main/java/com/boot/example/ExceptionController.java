package com.boot.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.ExceptionController
 *
 * @author lipeng
 * @date 2020/6/5 3:07 PM
 */
@RestController
public class ExceptionController {

    @RequestMapping("/exception")
    public void exception() {
        BusinessExceptionEnum.USER_NOT_FOUND.assertNotNull(null);
    }
}
