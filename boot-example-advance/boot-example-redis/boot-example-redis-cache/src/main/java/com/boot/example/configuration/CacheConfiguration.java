package com.boot.example.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.config.TransactionManagementConfigUtils;

/**
 * com.boot.example.configuration.RemoteRedisCacheConfiguration
 *
 * @author lipeng
 * @date 2021/6/1 8:11 PM
 */
public class CacheConfiguration {

    @Bean(name = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheAttributeSourceAdvisor transactionAdvisor(
            CacheAttributeSource cacheAttributeSource, CacheInterceptor cacheInterceptor) {

        BeanFactoryCacheAttributeSourceAdvisor advisor = new BeanFactoryCacheAttributeSourceAdvisor();
        advisor.setCacheAttributeSource(cacheAttributeSource);
        advisor.setAdvice(cacheInterceptor);
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAttributeSource cacheAttributeSource() {
        return new AnnotationCacheAttributeSource();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor transactionInterceptor(CacheAttributeSource cacheAttributeSource,
                                                   StringRedisTemplate stringRedisTemplate) {
        return new CacheInterceptor(cacheAttributeSource, stringRedisTemplate);
    }
}
