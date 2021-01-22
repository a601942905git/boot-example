import com.boot.example.annotation.Greet;
import com.boot.example.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * PACKAGE_NAME.MethodUtilsTest
 *
 * @author lipeng
 * @date 2021/1/22 4:50 PM
 */
@Slf4j
public class MethodUtilsTest {

    private static final ReflectionUtils.MethodFilter GREET_METHOD_FILTER = method ->
            AnnotatedElementUtils.hasAnnotation(method, Greet.class);

    @Test
    public void getGreetMethod() throws InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        for (Method selectMethod : MethodIntrospector.selectMethods(Person.class, GREET_METHOD_FILTER)) {
            log.info(selectMethod.getName() + "()方法执行返回结果：" + selectMethod.invoke(person));
        }
    }

    @Test
    public void operateGreetMethod() throws InvocationTargetException, IllegalAccessException {
        MethodIntrospector.MetadataLookup<String> metadataLookup = method -> {
            if (AnnotatedElementUtils.hasAnnotation(method, Greet.class)) {
                return method.getName();
            }
            return null;
        };
        Map<Method, String> map = MethodIntrospector.selectMethods(Person.class, metadataLookup);

        for (Map.Entry<Method, String> entry : map.entrySet()) {
            if (Objects.nonNull(entry.getValue())) {
                log.info("key===>" + entry.getKey().getName() + "，value===>" + entry.getValue());
            }
        }
    }
}
