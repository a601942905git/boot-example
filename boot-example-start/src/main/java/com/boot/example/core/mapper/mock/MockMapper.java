package com.boot.example.core.mapper.mock;

import com.boot.example.core.entity.mock.Mock;
import org.springframework.stereotype.Repository;

/**
 * com.boot.example.core.mapper.mock.MockMapper
 *
 * @author lipeng
 * @dateTime 2018/11/27 上午11:31
 */
@Repository
public class MockMapper {

    public Mock getMock(Integer id) {
        return Mock.builder().id(10001).name("mockService").build();
    }
}
