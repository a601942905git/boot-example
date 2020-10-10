package com.boot.example.factory;

import java.util.List;

/**
 * com.boot.example.Factoryable
 *
 * @author lipeng
 * @date 2019/11/7 下午4:27
 */
public interface FactoryAble<T extends FactorySupportType> {

    List<T> supportType();
}
