package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * @author lipeng
 * &#064;date 2025/3/31 17:49:33
 */
@Component
@Slf4j
public class FlowTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("任务监听器:{}", delegateTask);
    }
}
