package com.boot.example.test;

import com.boot.example.student.Student;
import com.boot.example.student.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * com.boot.example.test.TestService
 *
 * @author lipeng
 * @date 2019-02-19 11:33
 */
@Service
@Slf4j
public class TestService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TestService testService;

    /**
     * 非事务方法调用事务方法，会发现事务不起作用
     * @param student
     */
    public void middleMethod(Student student) {
        this.addStudent(student);
    }

    /**
     * 非事务方法调用事务方法，事务起作用
     * @param student
     */
    public void middleMethod1(Student student) {
        testService.addStudent(student);
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

    @Transactional(rollbackFor = Exception.class)
    public void test(Student student) {
        for (int i = 0; i < 2; i++) {
            student.setId(i + 1);
            this.save(student);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Student student) {
        studentMapper.saveStudent(student);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                System.out.println("transaction commit");
            }
        });
    }

    @Transactional
    public void transactionRollbackException() {
        try {
            testService.throwExceptionMethod();
        } catch (Exception e) {
            log.error("execute exception：", e);
        }
    }

    @Transactional
    public void throwExceptionMethod() {
        throw new NullPointerException();
    }
}
