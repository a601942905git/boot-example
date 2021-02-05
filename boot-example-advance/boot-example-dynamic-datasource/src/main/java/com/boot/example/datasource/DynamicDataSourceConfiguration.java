package com.boot.example.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * com.boot.example.DynamicDataSourceConfiguration
 *
 * 动态数据源流程：
 * controller ---> service ---> mapper ---> sqlSessionFactory ---> dataSource ---> getConnection()
 *
 * 动态数据源原理：
 * 1.自定义数据源并继承AbstractRoutingDataSource，使该数据源具备路由功能
 * 2.往自定义数据源中以map的形式添加目标数据源，设置默认数据源
 * 3.执行getConnection()时会调用determineCurrentLookupKey()方法获取key，根据key获取目标数据源，如果目标数据源为空，则使用默认数据源
 * 4.关键点就是要在自定义数据源重写父类AbstractRoutingDataSource#determineCurrentLookupKey()方法中指定数据源key
 * 5.key可以由使用者通过注解的方式指明，通过切面得到key并设置在ThreadLocal中，以便后面调用determineCurrentLookupKey()方法是可以从ThreadLocal中取出
 *
 *
 * @author lipeng
 * @date 2021/2/3 6:09 PM
 */
@Configuration
public class DynamicDataSourceConfiguration {

    @Bean(DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_MASTER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建DataSource对象
     * @ConfigurationProperties 注解用于设置数据源属性配置，如url、账号、密码
     *
     * @return 数据源对象
     */
    @Bean(DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_SLAVE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource(DataSource master, DataSource slave) {
        Map<Object, Object> dynamicDataSourceMap = new HashMap<>(2);
        dynamicDataSourceMap.put(DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_MASTER, master);
        dynamicDataSourceMap.put(DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_SLAVE, slave);
        // 设置动态数据源
        DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        dynamicDataSource.setTargetDataSources(dynamicDataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(master);
        return dynamicDataSource;
    }
}
