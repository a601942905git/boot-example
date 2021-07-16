package com.boot.example.service;

import com.boot.example.entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.service.StudentService
 *
 * @author lipeng
 * @date 2021/7/14 2:10 PM
 */
@Service
public class StudentService {

    public Student get() {
        Student student = new Student();
        student.setId(10001);
        student.setName("test1");
        return student;
    }

    public List<Student> list() {
        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(10001);
        student1.setName("test1");
        studentList.add(student1);

        Student student2 = new Student();
        student2.setId(10002);
        student2.setName("test2");
        studentList.add(student2);

        Student student3 = new Student();
        student3.setId(10003);
        student3.setName("test3");
        studentList.add(student3);
        return studentList;
    }
}
