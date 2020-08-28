package com.boot.example.placeholder;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.ApolloApplication
 *
 * @author lipeng
 * @dateTime 2018/12/10 上午9:55
 */
@SpringBootApplication
@EnableApolloConfig
@Slf4j
public class ApolloApplication {

    private static TestJavaConfigBean testJavaConfigBean;

    private static SampleRedisConfig sampleRedisConfig;

    private static DatasourceConfig datasourceConfig;

    @Autowired
    public void setTestJavaConfigBean(TestJavaConfigBean testJavaConfigBean) {
        ApolloApplication.testJavaConfigBean = testJavaConfigBean;
    }

    @Autowired
    public void setSampleRedisConfig(SampleRedisConfig sampleRedisConfig) {
        ApolloApplication.sampleRedisConfig = sampleRedisConfig;
    }

    @Autowired
    public void setDatasourceConfig(DatasourceConfig datasourceConfig) {
        ApolloApplication.datasourceConfig = datasourceConfig;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ApolloApplication.class, args);
        log.info("TestJavaConfigBean======>" + testJavaConfigBean);
        log.info("SampleRedisConfig======>" + sampleRedisConfig);
        log.info("DatasourceConfig======>" + datasourceConfig);

        log.info("apolloApi key=batch，value=" + ApolloApi.getConfig("batch"));
        log.info("apolloApi key=timeout，value=" + ApolloApi.getConfig("timeout"));
    }
}
