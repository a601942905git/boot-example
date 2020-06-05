package com.boot.example;

import java.util.Objects;

/**
 * com.boot.example.Assert
 *
 * @author lipeng
 * @date 2020/6/5 3:16 PM
 */
public interface Assert {

    BusinessException newException();

    default void assertNotNull(Object object) {
        if (Objects.isNull(object)) {
            throw newException();
        }
    }
}
