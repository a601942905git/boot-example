package com.boot.example.placeholder;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.placeholder.AppRefreshConfig
 *
 * @author lipeng
 * @date 2020/2/25 下午5:18
 */
@Component
@Slf4j
public class AppRefreshConfig {

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private SampleRedisConfig sampleRedisConfig;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        log.info("before refresh {}", sampleRedisConfig.toString());
        refreshScope.refresh("sampleRedisConfig");
        log.info("after refresh {}", sampleRedisConfig.toString());
    }

    @ApolloConfigChangeListener(value = "order-service.dataSource")
    public void onChange1(ConfigChangeEvent changeEvent) {
        log.info("before refresh {}", sampleRedisConfig.toString());
        refreshScope.refresh("sampleRedisConfig");
        log.info("after refresh {}", sampleRedisConfig.toString());
    }
}
