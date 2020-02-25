package com.boot.example.placeholder;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
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

        while (true) {
            System.out.println("TestJavaConfigBean======>" + testJavaConfigBean);

            System.out.println("SampleRedisConfig======>" + sampleRedisConfig);

            System.out.println("DatasourceConfig======>" + datasourceConfig);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
