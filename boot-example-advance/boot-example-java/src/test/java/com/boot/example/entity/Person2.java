package com.boot.example.entity;

/**
 * @author lipeng
 * &#064;date 2024/7/18 18:03:01
 */
public class Person2 {

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
