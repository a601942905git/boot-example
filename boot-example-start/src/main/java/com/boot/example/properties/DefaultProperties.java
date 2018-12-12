package com.boot.example.properties;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.properties.Test
 *
 * @author lipeng
 * @dateTime 2018/12/7 下午2:15
 */
@Configuration
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefaultProperties {


    @Value("${testId:10001}")
    private Integer testId;

    @Value("${testName:测试名称}")
    private String testName;
}
