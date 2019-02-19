package com.boot.example.test;

import com.boot.example.student.Student;
import com.boot.example.student.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.boot.example.test.TestService
 *
 * @author lipeng
 * @date 2019-02-19 11:33
 */
@Service
public class TestService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 非事务方法调用事务方法，会发现事务不起作用
     * @param student
     */
    public void middleMethod(Student student) {
        this.addStudent(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addStudent(Student student) {
        this.studentMapper.saveStudent(student);
        System.out.println(1/ 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized void addStudentAge(Integer id) throws InterruptedException {
        Student student = studentMapper.getStudentById(id);
        studentMapper.updateStudentAgeById(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addStudentAge1(Integer id) throws InterruptedException {
        Student student = studentMapper.getStudentById(id);
        studentMapper.updateStudentAgeById(student);
    }
}
