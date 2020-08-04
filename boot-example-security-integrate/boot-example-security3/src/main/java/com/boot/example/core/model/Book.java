package com.boot.example.core.model;

import lombok.*;

/**
 * com.boot.example.core.model.Book
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午3:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    private Integer id;

    private String name;

    private String publishDate;
}
