package com.boot.example.controller.student;

import com.boot.example.core.entity.StudentEntity;
import com.boot.example.core.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public StudentEntity getStudent(@PathVariable(name = "id") Integer id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/name")
    public StudentEntity getStudentByName() {
        String desc = "'' and 1 = 1;DELETE FROM student;";
        return studentService.getStudentByName(desc);
    }
}
