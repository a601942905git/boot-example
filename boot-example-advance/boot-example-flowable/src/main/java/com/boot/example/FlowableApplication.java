package com.boot.example;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author lipeng
 * &#064;date 2024/8/27 19:23:16
 */
@SpringBootApplication
public class FlowableApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(RepositoryService repositoryService, RuntimeService runtimeService, TaskService taskService) {
        return args -> {
            System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
            System.out.println("Number of tasks: " + taskService.createTaskQuery().count());
            runtimeService.startProcessInstanceByKey("simple");
            System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
        };

    }
}
