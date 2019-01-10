package com.boot.example.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.convert.AppController
 *
 * @author lipeng
 * @date 2019/1/9 下午1:18
 */
@RestController
public class AppController {

    @Autowired
    private AppProperties appProperties;

    @RequestMapping("/convert")
    public AppProperties index() {
        return appProperties;
    }
}
