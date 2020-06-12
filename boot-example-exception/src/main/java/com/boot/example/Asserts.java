package com.boot.example;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * com.boot.example.Assert
 *
 * @author lipeng
 * @date 2020/6/5 3:16 PM
 */
public interface Asserts {

    BusinessException newException();

    /**
     * 判断null
     *
     * @param object 对象
     */
    default void assertNotNull(Object object) {
        if (Objects.isNull(object)) {
            throw newException();
        }
    }

    /**
     * Collection集合非空校验
     *
     * @param collection 集合对象
     */
    default void assertNotEmpty(Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            throw newException();
        }
    }

    /**
     * Map集合非空校验
     *
     * @param map 集合对象
     */
    default void assertNotEmpty(Map<?, ?> map) {
        if (CollectionUtils.isEmpty(map)) {
            throw newException();
        }
    }

    /**
     * 条件判断
     *
     * @param predicate 执行业务
     */
    default void assertNotFalse(Predicate predicate) {
        if (!predicate.test()) {
            throw newException();
        }
    }

    interface Predicate {
        boolean test();
    }
}
