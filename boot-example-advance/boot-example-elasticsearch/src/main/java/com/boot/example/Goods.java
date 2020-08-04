package com.boot.example;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * com.boot.example.Order
 *
 * @author lipeng
 * @date 2019/11/6 下午2:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = IndexConstant.GOODS_INDEX)
public class Goods implements Serializable {

    @Id
    private Long id;

    @Field
    private String name;

    @Field
    private String title;

    @Field
    private BigDecimal price;

    @Field
    private String publishDate;
}
