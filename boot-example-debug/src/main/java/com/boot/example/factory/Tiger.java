package com.boot.example.factory;

import lombok.Data;

/**
 * com.boot.example.entity.Tigger
 *
 * @author lipeng
 * @date 2021/1/25 9:04 PM
 */
@Data
public class Tiger {

    public String hello() {
        return "say hello";
    }
}
