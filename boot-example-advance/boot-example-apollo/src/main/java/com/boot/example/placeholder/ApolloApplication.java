package com.boot.example.placeholder;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

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

    private static RabbitmqConfig rabbitmqConfig;

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

    @Autowired
    public void setRabbitmqConfig(RabbitmqConfig rabbitmqConfig) {
        ApolloApplication.rabbitmqConfig = rabbitmqConfig;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ApolloApplication.class, args);
        while (true) {
            // @value方式读取配置
            log.info("TestJavaConfigBean======>" + testJavaConfigBean);
            log.info("DatasourceConfig======>" + datasourceConfig);

            // @ConfigurationProperties方式读取配置
            log.info("SampleRedisConfig======>" + sampleRedisConfig);

            // api方式读取properties配置
            log.info("apolloApi key=batch，value=" + ApolloApi.getDefaultIntegerConfig("batch"));
            log.info("apolloApi key=timeout，value=" + ApolloApi.getDefaultIntegerConfig("timeout"));

            // api方式读取yml配置
            log.info("apolloApi key=spring.rabbitmq.address，value=" + ApolloApi.getRabbitmqStringConfig("spring.rabbitmq.address"));
            log.info("apolloApi key=spring.rabbitmq.port，value=" + ApolloApi.getRabbitmqIntegerConfig("spring.rabbitmq.port"));
            log.info("RabbitmqConfig======>" + rabbitmqConfig);

            TimeUnit.SECONDS.sleep(10);
        }
    }
}
