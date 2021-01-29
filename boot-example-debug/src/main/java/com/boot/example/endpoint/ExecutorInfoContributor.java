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
        info.put("poolSize", executor.getPoolSize());
        info.put("corePoolSize", executor.getCorePoolSize());
        info.put("maximumPoolSize", executor.getMaximumPoolSize());
        info.put("largestPoolSize", executor.getLargestPoolSize());
        info.put("completedTaskCount", executor.getCompletedTaskCount());
        info.put("taskCount", executor.getTaskCount());
        info.put("queueSize", executor.getQueue().size());
        info.put("queueRemainingCapacity", executor.getQueue().remainingCapacity());
        return info;
    }
}
