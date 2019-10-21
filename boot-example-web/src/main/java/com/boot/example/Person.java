package com.boot.example;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.boot.example.Person
 *
 * @author lipeng
 * @date 2019/10/21 下午8:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Person {

    private Integer personId;

    private String personName;

    private Integer personAge;
}
