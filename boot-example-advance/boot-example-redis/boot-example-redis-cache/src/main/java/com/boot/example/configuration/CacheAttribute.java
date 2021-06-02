package com.boot.example.configuration;

import lombok.Data;

/**
 * com.boot.example.configuration.CacheAttribute
 *
 * @author lipeng
 * @date 2021/6/2 10:50 AM
 */
@Data
public class CacheAttribute {

    private String name;

    private String key;

    private Long ttl;

    private Long randomTtl;
}
