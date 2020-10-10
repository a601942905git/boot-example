package com.boot.example.factory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.boot.example.BaseFactory
 *
 * @author lipeng
 * @date 2019/11/7 下午4:24
 */
public abstract class BaseFactory<T extends FactoryAble<?>> implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<FactorySupportType, T> factoryMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Class<?> currentClass = this.getClass();
        Type superclass = currentClass.getGenericSuperclass();
        // 获取泛型参数类型
        Type[] actualTypeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
        if (null == actualTypeArguments || actualTypeArguments.length == 0) {
            throw new NullPointerException("please set factory generic type");
        }

        Type actualTypeArgument = actualTypeArguments[0];
        Class<T> genericClass = (Class<T>) actualTypeArgument;

        // 获取所有的服务实例
        Map<String, T> serviceInstanceMap = applicationContext.getBeansOfType(genericClass);
        if (CollectionUtils.isEmpty(serviceInstanceMap)) {
            throw new NullPointerException("service instance not exists");
        }

        for (Map.Entry<String, T> entry : serviceInstanceMap.entrySet()) {
            List<? extends FactorySupportType> supportTypeList = entry.getValue().supportType();
            if (CollectionUtils.isEmpty(supportTypeList)) {
                throw new NullPointerException("please set support type");
            }

            for (FactorySupportType supportType : supportTypeList) {
                factoryMap.put(supportType, entry.getValue());
            }
        }
    }

    public T getBean(FactorySupportType factoryObjectType) {
        return factoryMap.get(factoryObjectType);
    }
}
