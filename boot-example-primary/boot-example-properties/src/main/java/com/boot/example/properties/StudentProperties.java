package com.boot.example.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * com.boot.example.properties.StudentProperties
 *
 * @author lipeng
 * @date 2019/1/9 上午10:49
 */
@ConfigurationProperties(prefix = "student")
public class StudentProperties {

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
}
