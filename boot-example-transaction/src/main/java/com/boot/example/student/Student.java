package com.boot.example.student;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

/**
 * com.boot.example.student.Student
 *
 * @author lipeng
 * @date 2019-02-01 10:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Student {

    private Integer id;

    private String name;

    private Integer age;

    private String stuDesc;
}
