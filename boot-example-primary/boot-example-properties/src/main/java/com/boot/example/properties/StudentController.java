package com.boot.example.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.properties.StudentController
 *
 * @author lipeng
 * @date 2019/1/9 上午10:51
 */
@RestController
public class StudentController {

    @Autowired
    private StudentProperties studentProperties;

    @RequestMapping("/student")
    public StudentProperties index() {
        return studentProperties;
    }
}
