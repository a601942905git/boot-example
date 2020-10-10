package com.boot.example.cache;

import lombok.*;

/**
 * com.boot.example.cache.Student
 *
 * @author lipeng
 * @dateTime 2018/12/4 下午4:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    private Integer id;

    private String name;
}
