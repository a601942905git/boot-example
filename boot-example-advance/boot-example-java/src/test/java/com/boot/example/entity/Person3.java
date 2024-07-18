package com.boot.example.entity;

/**
 * 测试Boolean类型变量不能以is开头问题
 *
 * @author lipeng
 * &#064;date 2024/7/18 18:03:26
 */
public class Person3 {

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
