package com.boot.example;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.StudentController
 *
 * @author lipeng
 * @date 2019/10/21 下午7:30
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private static List<Student> studentList = new ArrayList<>();

    static {
        studentList.add(new Student(100001, "jack", 22));
        studentList.add(new Student(100002, "lucky", 23));
        studentList.add(new Student(100003, "jone", 24));
        studentList.add(new Student(100004, "lucy", 25));
        studentList.add(new Student(100005, "lily", 26));
    }

    @GetMapping("/")
    public List<Student> listStudent() {
        return studentList;
    }

    @GetMapping("id/{id}")
    public Student getStudent(@PathVariable("id") Integer id) {
        return studentList.stream()
                .filter(student -> Objects.equals(student.getId(), id))
                .findFirst()
                .orElse(new Student(999999, "default", 18));
    }

    @PostMapping("/")
    public List<Student> addStudent(@RequestBody Student student) {
        studentList.add(student);
        return studentList;
    }

    @PutMapping("/")
    public List<Student> updateStudent(@RequestBody Student student) {
        studentList.removeIf(stu -> Objects.equals(stu.getId(), student.getId()));
        studentList.add(student);
        return studentList;
    }
}
