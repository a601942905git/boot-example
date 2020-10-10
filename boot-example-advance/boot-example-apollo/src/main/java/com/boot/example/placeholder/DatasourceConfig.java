package com.boot.example.placeholder;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.placeholder.DatasourceConfig
 *
 * @author lipeng
 * @date 2020/2/25 下午5:50
 */
@Component
@Data
@ToString
public class DatasourceConfig {

    @Value("${spring.datasource.url:''}")
    private String url;

    @Value("${spring.datasource.username:''}")
    private String userName;

    @Value("${spring.datasource.password:''}")
    private String password;
}
