package com.boot.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.boot.example.entity.Student
 *
 * @author lipeng
 * @date 2021/6/2 11:43 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer id;

    private String name;
}
