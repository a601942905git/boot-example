package com.boot.example.placeholder;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.ApolloApplication
 *
 * @author lipeng
 * @dateTime 2018/12/10 上午9:55
 */
@SpringBootApplication
@EnableApolloConfig
public class ApolloApplication {

    private static TestJavaConfigBean testJavaConfigBean;

    @Autowired
    public void setTestJavaConfigBean(TestJavaConfigBean testJavaConfigBean) {
        ApolloApplication.testJavaConfigBean = testJavaConfigBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApolloApplication.class, args);

        System.out.println("TestJavaConfigBean======>" + testJavaConfigBean);
    }
}
