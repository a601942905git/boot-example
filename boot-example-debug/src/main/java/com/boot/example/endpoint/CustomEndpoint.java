package com.boot.example.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * com.boot.example.endpoint.CustomEndpoint
 *
 * 自定义Endpoint：
 * 1.创建Endpoint，使用@Endpoint注解标注，id中不能含有"/"
 * 2.创建自动配置类，声明Endpoint为IOC容器中的Bean
 * 3.配置文件中添加：management.endpoints.web.exposure.include: health, info, custom
 *
 * @author lipeng
 * @date 2021/1/22 3:41 PM
 */
@Endpoint(id = "custom")
public class CustomEndpoint {

    @ReadOperation
    public Map<String, Object> custom() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "endpoint");
        return map;
    }
}
