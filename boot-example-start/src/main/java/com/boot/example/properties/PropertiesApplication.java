package com.boot.example.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.properties.PropertiesApplication
 *
 * @author lipeng
 * @dateTime 2018/11/22 上午9:20
 */
@SpringBootApplication
public class PropertiesApplication {

    private static Person person;

    @Autowired
    public void setPerson(Person person) {
        PropertiesApplication.person = person;
    }

    private static Student student;

    @Autowired
    public void setStudent(Student student) {
        PropertiesApplication.student = student;
    }

    private static Teacher teacher;

    @Autowired
    public void setTeacher(Teacher teacher) {
        PropertiesApplication.teacher = teacher;
    }

    private static Actor actor;

    @Autowired
    public void setActor(Actor actor) {
        PropertiesApplication.actor = actor;
    }

    private static DefaultProperties defaultProperties;

    @Autowired
    public void setTest(DefaultProperties defaultProperties) {
        PropertiesApplication.defaultProperties = defaultProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(PropertiesApplication.class, args);
        System.out.println(person);

        System.out.println(student);

        System.out.println(teacher);

        System.out.println(actor);

        System.out.println(defaultProperties);
    }
}
