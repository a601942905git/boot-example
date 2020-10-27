package com.boot.example.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

/**
 * com.boot.example.user.User
 *
 * @author lipeng
 * @date 2019-02-01 11:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

    private Integer id;

    private String name;


    private Integer age;

}
