package com.boot.example.entity;

/**
 * @author lipeng
 * &#064;date 2024/7/18 18:02:27
 */
public class Person1 {

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
