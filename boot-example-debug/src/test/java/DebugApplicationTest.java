import com.boot.example.DebugApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * PACKAGE_NAME.DebugController
 * 1.SpringBootTest.WebEnvironment.MOCK：不启动内嵌web服务
 * 2.SpringBootTest.WebEnvironment.DEFINED_PORT：以8080端口启动web服务
 * 3.SpringBootTest.WebEnvironment.RANDOM_PORT：以随机端口启动web服务
 * 4.SpringBootTest.WebEnvironment.NONE：不提供web环境
 *
 *
 * @author lipeng
 * @date 2021/1/6 3:53 PM
 */
@SpringBootTest(classes = DebugApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class DebugApplicationTest {

    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";

    @Test
    public void test() {
        log.info("执行测试任务......");
    }

    @Test
    public void springFactoriesTest() {
        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
        if (Objects.isNull(defaultClassLoader)) {
            defaultClassLoader = DebugApplicationTest.class.getClassLoader();
        }

        try {
            Enumeration<URL> urls = defaultClassLoader.getResources(FACTORIES_RESOURCE_LOCATION);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                UrlResource urlResource = new UrlResource(url);
                Properties properties = PropertiesLoaderUtils.loadProperties(urlResource);
                for (Map.Entry<?, ?> entry : properties.entrySet()) {
                    log.info("spring.factories key===>" + entry.getKey());
                    log.info("spring.factories value===>" + entry.getValue());
                }

            }
        } catch (IOException e) {
            log.error("io exception：", e);
        }

    }
}
