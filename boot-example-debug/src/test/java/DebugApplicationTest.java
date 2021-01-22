import com.boot.example.DebugApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void test() {
        log.info("执行测试任务......");
    }
}
