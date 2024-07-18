package com.boot.example;

import javassist.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * -XX:MaxMetaspaceSize=6m --add-opens java.base/java.lang=ALL-UNNAMED
 *
 * @author lipeng
 * &#064;date 2024/6/12 15:13:26
 */
public class MetaspaceTest {

    // 在全局范围内保持对类的引用，防止垃圾收集器回收
    private static final List<Class<?>> loadedClasses = new ArrayList<>();

    // 自定义类加载器，不存在实际用途，仅为示意
    static class CustomClassLoader extends ClassLoader {
    }

    @Test
    public void outOfMemoryTest() throws CannotCompileException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 设置 Metaspace 大小限制
        System.out.println("Starting to generate classes...");
        for (int i = 0; ; i++) {
            ClassLoader classLoader = new CustomClassLoader();
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.makeClass("com.example.GeneratedClass" + i);
            CtMethod method = CtNewMethod.make("public void sayHello() { System.out.println(\"Hello, I am class " + i + "\"); }", ctClass);
            ctClass.addMethod(method);
            Class<?> clazz = ctClass.toClass(classLoader, null);
            clazz.getDeclaredMethod("sayHello").invoke(clazz.getDeclaredConstructor().newInstance());

            // 非常重要：需要保持一定的对象来确保它们不会被 GC 回收
            loadedClasses.add(clazz);

            // 打印已生成类的数量
            if (i % 100 == 0) {
                System.out.println("Generated " + i + " classes");
            }
        }
    }

}
