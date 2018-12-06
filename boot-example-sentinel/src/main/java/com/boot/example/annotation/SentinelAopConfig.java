package com.boot.example.annotation;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.annotation.SentinelAopConfig
 *
 * @author lipeng
 * @dateTime 2018/12/5 上午10:30
 */
@Configuration
public class SentinelAopConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
