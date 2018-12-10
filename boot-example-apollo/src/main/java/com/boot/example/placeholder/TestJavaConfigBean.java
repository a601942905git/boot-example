package com.boot.example.placeholder;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * com.boot.example.TestJavaConfigBean
 *
 * @author lipeng
 * @dateTime 2018/12/10 上午9:53
 */
@Data
@ToString
public class TestJavaConfigBean {

    @Value("${timeout:100}")
    private int timeout;

    @Value("${batch:200}")
    private int batch;
}
