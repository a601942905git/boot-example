package com.boot.example.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * @author lipeng
 * &#064;date 2024/7/18 18:04:07
 */
public class BooleanTest {

    @Test
    public void test01() throws JsonProcessingException {
        Person1 person1 = new Person1();
        person1.setDeleted(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(person1);
        System.out.println("序列化结果：" + jsonStr);
        Gson gson = new Gson();
        Person1 person1FromJson = gson.fromJson(jsonStr, Person1.class);
        System.out.println("反序列化结果：" + person1FromJson.toString());
    }

    @Test
    public void test02() throws JsonProcessingException {
        Person2 person2 = new Person2();
        person2.setDeleted(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(person2);
        System.out.println("序列化结果：" + jsonStr);
        Gson gson = new Gson();
        Person2 person2FromJson = gson.fromJson(jsonStr, Person2.class);
        System.out.println("反序列化结果：" + person2FromJson.toString());
    }

    @Test
    public void test03() throws JsonProcessingException {
        Person3 person3 = new Person3();
        person3.setDeleted(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(person3);
        System.out.println("序列化结果：" + jsonStr);
        Gson gson = new Gson();
        Person3 person3FromJson = gson.fromJson(jsonStr, Person3.class);
        System.out.println("反序列化结果：" + person3FromJson.toString());
    }

    @Test
    public void test04() throws JsonProcessingException {
        Person4 person4 = new Person4();
        person4.setIsDeleted(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(person4);
        System.out.println("序列化结果：" + jsonStr);
        Gson gson = new Gson();
        Person4 person4FromJson = gson.fromJson(jsonStr, Person4.class);
        System.out.println("反序列化结果：" + person4FromJson.toString());
    }

    public static class Person1 {

        private boolean isDeleted;

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        @Override
        public String toString() {
            return "Person1{" +
                    "isDeleted=" + isDeleted +
                    '}';
        }
    }

    public static class Person2 {

        private boolean deleted;

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        @Override
        public String toString() {
            return "Person2{" +
                    "deleted=" + deleted +
                    '}';
        }
    }

    public static class Person3 {

        Boolean isDeleted;

        public Boolean getDeleted() {
            return isDeleted;
        }

        public void setDeleted(Boolean deleted) {
            isDeleted = deleted;
        }

        @Override
        public String toString() {
            return "Person3{" +
                    "isDeleted=" + isDeleted +
                    '}';
        }
    }

    @Data
    public static class Person4 {
        Boolean isDeleted;
    }
}
