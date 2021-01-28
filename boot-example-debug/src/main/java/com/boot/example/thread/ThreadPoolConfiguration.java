package com.boot.example.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * com.boot.example.thread.ThreadPoolConfigu
 *
 * @author lipeng
 * @date 2020/11/5 5:19 PM
 */
@Component
public class ThreadPoolConfiguration {

    /**
     * 如果不自定义线程池，异步任务会使用TaskExecutionAutoConfiguration配置类中定的线程池
     *
     * @return 线程池
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 设置核心线程数大小
        threadPoolTaskExecutor.setCorePoolSize(10);
        // 设置最大线程数大小
        threadPoolTaskExecutor.setMaxPoolSize(10);
        // 设置队列大小
        threadPoolTaskExecutor.setQueueCapacity(5000);
        // 是否允许核心线程数超时
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        // 线程存活时间
        threadPoolTaskExecutor.setKeepAliveSeconds(120);
        // 线程池关闭等待时间
        threadPoolTaskExecutor.setAwaitTerminationSeconds(120);
        // 当线程池关闭时是否等待任务执行完成
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix("thread-pool-task-executor");
        // 设置拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolTaskExecutor;
    }
}
