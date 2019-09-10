package com.boot.example.test;

import com.boot.example.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.boot.example.test.TestController
 *
 * @author lipeng
 * @date 2019-02-19 11:30
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/addStudent")
    public void addStudent(@RequestBody Student student) {
        testService.middleMethod(student);
    }

    @RequestMapping("/addStudent1")
    public void addStudent1(@RequestBody Student student) {
        testService.middleMethod1(student);
    }

    /**
     * 调用1000次，原本当前student的age应该为1000，但是通过测试发现达不到1000
     * 由于调用的service方法既是用来同步也开启了事务，事务通过动态代理来实现，可能方法执行完了，
     * 事务还没有提交，此时其余的线程进入方法，就会造成最终数据达不到1000
     * @param id
     */
    @RequestMapping("/addStudentAge/{id}")
    public void addStudentAge(@PathVariable(name = "id") Integer id){
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    testService.addStudentAge(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 对上面的方法进行改进，将同步控制在事务之外，保证在事务开启-事务提交这期间是同步的
     * @param id
     */
    @RequestMapping("/addStudentAge1/{id}")
    public void addStudentAge1(@PathVariable(name = "id") Integer id){
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    synchronized (this) {
                        testService.addStudentAge1(id);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @PostMapping("/")
    public void test(@RequestBody Student student) {
        testService.test(student);
    }
}
