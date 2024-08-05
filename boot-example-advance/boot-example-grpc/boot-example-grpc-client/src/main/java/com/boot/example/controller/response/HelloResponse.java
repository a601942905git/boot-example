package com.boot.example.controller.response;

import lombok.Data;

import java.util.List;

/**
 * @author lipeng
 * &#064;date 2024/8/5 18:03:49
 */
@Data
public class HelloResponse {

    private List<String> names;

    private String msg;
}
