package com.boot.example;

import com.boot.example.petstore.client.api.UserControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.SwaggerClientController
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午1:42
 */
@RestController
public class SwaggerClientController {

    @Autowired
    private UserControllerApi userControllerApi;

    @RequestMapping("/")
    public void index() {
        System.out.println(userControllerApi.getUserUsingGET(10001));
    }
}
