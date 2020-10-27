package com.boot.example;

import lombok.*;

import java.time.LocalDateTime;

/**
 * com.boot.example.Student
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午3:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    private Integer id;

    private LocalDateTime localDateTime;
}
