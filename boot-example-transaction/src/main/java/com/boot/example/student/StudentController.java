package com.boot.example.student;

import com.boot.example.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.boot.example.student.StudentController
 *
 * @author lipeng
 * @date 2019-02-01 10:26
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/")
    public Integer addStudent(@RequestBody Student student) {
        return this.studentService.saveStudent(student);
    }

    @PostMapping("/common")
    public Integer saveCommon(@RequestBody Student student) {
        User user = new User();
        BeanUtils.copyProperties(student, user);
        return this.studentService.saveCommon(student, user);
    }

    @PostMapping("/addStudentByManualTransaction")
    public Integer addStudentByManualTransaction(@RequestBody Student student) {
        return studentService.addStudentByManualTransaction(student);
    }
}
