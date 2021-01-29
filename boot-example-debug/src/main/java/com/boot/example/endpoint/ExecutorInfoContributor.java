package com.boot.example.endpoint;

import com.boot.example.util.ApplicationContextUtils;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * com.boot.example.endpoint.ExecutorInfoContributor
 * 线程池信息监控
 *
 * @author lipeng
 * @date 2021/1/28 3:03 PM
 */
@Component
public class ExecutorInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, ThreadPoolTaskExecutor> beansOfType =
                ApplicationContextUtils.getApplicationContext().getBeansOfType(ThreadPoolTaskExecutor.class);
        for (Map.Entry<String, ThreadPoolTaskExecutor> entry : beansOfType.entrySet()) {
            builder.withDetail(entry.getKey(), buildThreadPoolInfo(entry.getValue()));
        }
    }

    private static Map<String, Object> buildThreadPoolInfo(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        ThreadPoolExecutor executor = threadPoolTaskExecutor.getThreadPoolExecutor();
        Map<String, Object> info = new HashMap<>(8);
        info.put("terminated", executor.isTerminated());
        info.put("pool.size", executor.getPoolSize());
        info.put("core.pool.size", executor.getCorePoolSize());
        info.put("maximum.pool.size", executor.getMaximumPoolSize());
        info.put("largest.pool.size", executor.getLargestPoolSize());
        info.put("completed.task.count", executor.getCompletedTaskCount());
        info.put("task.count", executor.getTaskCount());
        info.put("queue.size", executor.getQueue().size());
        info.put("queue.remaining.capacity", executor.getQueue().remainingCapacity());
        return info;
    }
}
