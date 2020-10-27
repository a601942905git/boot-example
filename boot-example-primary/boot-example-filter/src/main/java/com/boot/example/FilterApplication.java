package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * com.boot.example.FilterApplicaiton
 *
 * @author lipeng
 * @date 2019/10/27 下午8:44
 */
@SpringBootApplication
@ServletComponentScan
public class FilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilterApplication.class);
    }
}
