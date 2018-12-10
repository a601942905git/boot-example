package com.boot.example.placeholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.ApolloController
 *
 * @author lipeng
 * @dateTime 2018/12/10 上午10:50
 */
@RestController
@RequestMapping("/testJavaConfigBean")
public class ApolloController {

    @Autowired
    private TestJavaConfigBean testJavaConfigBean;

    @RequestMapping("/")
    public TestJavaConfigBean testJavaConfigBean() {
        return testJavaConfigBean;
    }
}
