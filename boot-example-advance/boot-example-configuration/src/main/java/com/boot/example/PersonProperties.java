package com.boot.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * com.boot.example.PersonProperties
 *
 * 1.配置属性的自动提示需要在pom.xml中添加spring-boot-configuration-processor，
 * 用于生成元数据信息，元数据信息保存在spring-configuration-metadata.json文件中
 *
 * 2.配置属性值自动提示需要在META-INF目录下创建additional-spring-configuration-metadata.json文件，
 * 通过hints节点添加可供选择的属性值
 *
 * @author lipeng
 * @date 2021/2/3 10:45 AM
 */
@ConfigurationProperties(prefix = "person")
public class PersonProperties {

    private Integer id;

    private String name;

    private Teacher teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public static class Teacher {

        private Integer id;

        private String name;

        private Student student;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        @Override
        public String toString() {
            return "Teacher{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", student=" + student +
                    '}';
        }
    }

    public static class Student {
        private Integer id;

        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PersonProperties{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
