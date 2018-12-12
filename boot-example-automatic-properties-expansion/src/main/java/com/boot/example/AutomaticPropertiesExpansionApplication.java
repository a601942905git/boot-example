package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * com.boot.example.AutomaticPropertiesExpansionApplication
 *
 * @author lipeng
 * @dateTime 2018/12/12 下午5:29
 */
@SpringBootApplication
public class AutomaticPropertiesExpansionApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(AutomaticPropertiesExpansionApplication.class, args);

        Environment environment = applicationContext.getEnvironment();

        System.out.println("项目名称：" + environment.getProperty("project.name"));
        System.out.println("项目版本：" + environment.getProperty("project.version"));
        System.out.println("项目artifactId：" + environment.getProperty("project.artifactId"));
    }
}
