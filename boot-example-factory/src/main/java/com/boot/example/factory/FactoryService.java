package com.boot.example.factory;

import java.util.List;

/**
 *
 * @author lipeng
 * @date 2019/11/7 下午4:27
 */
public interface FactoryService<T extends FactoryServiceType> {

    List<T> supportType();
}
