package com.boot.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.CustomerContextPathController
 *
 * @author lipeng
 * @dateTime 2018/12/12 下午4:17
 */
@RestController
public class CustomerContextPathController {

    @RequestMapping("/custom")
    public List<String> index(HttpServletRequest request) {
        List<String> stringList = new ArrayList<>();
        stringList.add("custom context path：" + request.getContextPath());
        stringList.add("custom request url：" + request.getRequestURL());
        stringList.add("custom request uri：" + request.getRequestURI());
        return stringList;
    }
}
