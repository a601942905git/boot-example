package com.boot.example.listener;

import com.boot.example.util.ApplicationContextUtils;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.boot.example.lisenter.ApplicationEnviromentListener
 * ApplicationEnvironmentPreparedEvent事件是在容器刷新之前发布的
 * 所以该监听器不能使用@Component注解来定义，可以通过如下方式来定义监听器：
 * 1.SpringApplication springApplication = new SpringApplication(FrameworkLearningApplication.class);
 *   springApplication.addListeners(new ApplicationEnvironmentListener());
 *   springApplication.run(args);
 * 2. 或者在resources目录下面创建META-INF文件夹，在META-INF文件夹下面创建spring.factories文件，文件内容如下：
 * org.springframework.context.ApplicationListener=\
 *   com.boot.example.listener.ApplicationEnvironmentListener
 *
 * @author lipeng
 * @date 2020/1/7 上午11:21
 */
public class ApplicationEnvironmentListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ApplicationContextUtils.setEnvironment(event.getEnvironment());
    }
}
