package com.boot.example;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * com.boot.example.User
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午3:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String name;

    private LocalDate localDate;

    private LocalDateTime localDateTime;
}
