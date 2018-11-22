package com.boot.example.controller.student;

import com.boot.example.core.entity.StudentEntity;
import com.boot.example.core.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.boot.example.controller.student.StudentController
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午7:28
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/")
    public List<StudentEntity> listStudent() {
        List<StudentEntity> studentEntityList = studentService.listStudent();
        return studentEntityList;
    }
}
