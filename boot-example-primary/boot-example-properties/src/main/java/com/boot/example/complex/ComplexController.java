package com.boot.example.complex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.complex.ComplexController
 *
 * @author lipeng
 * @date 2019/1/9 上午11:12
 */
@RestController
public class ComplexController {

    @Autowired
    private ComplexStudentProperties complexStudentProperties;

    @RequestMapping("/complex")
    public ComplexStudentProperties index() {
        return complexStudentProperties;
    }
}
