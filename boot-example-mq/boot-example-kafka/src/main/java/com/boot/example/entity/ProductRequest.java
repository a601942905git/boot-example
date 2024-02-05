package com.boot.example.entity;

import lombok.Data;

/**
 * com.boot.example.entity.ProductRequest
 *
 * @author lipeng
 * @date 2022/11/9 10:06 AM
 */
@Data
public class ProductRequest {

    private String msgNo;

    private Product product;

    @Data
    public static class Product {

        private String code;

        private String name;
    }

}
