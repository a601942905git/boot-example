package com.boot.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * com.boot.example.async.config.ThreadPoolConfiguration
 *
 * @author lipeng
 * @date 2018/12/24 上午9:38
 */
@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列大小
        executor.setQueueCapacity(500);
        // 设置线程最大空闲时间
        executor.setKeepAliveSeconds(120);
        // 设置当程序关闭后线程池中任务的等待时间
        executor.setAwaitTerminationSeconds(120);
        // 当程序关闭后，等待线程池中的任务执行完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中线程的名称前缀
        executor.setThreadNamePrefix("async-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
