package com.boot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author lipeng
 * @since 2020-06-25 20:07:44
 */
@Data
public class User implements Serializable {

private static final long serialVersionUID = -47603433138050415L;
    
    
    private Integer id;
    
    
    private String name;
    
    
    private Integer age;
    
}