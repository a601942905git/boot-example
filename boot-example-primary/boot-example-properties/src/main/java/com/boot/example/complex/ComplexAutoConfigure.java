package com.boot.example.complex;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.complex.ComplexAutoConfigure
 *
 * @author lipeng
 * @date 2019/1/9 上午11:26
 */
@Configuration
@EnableConfigurationProperties(ComplexStudentProperties.class)
public class ComplexAutoConfigure {
}
