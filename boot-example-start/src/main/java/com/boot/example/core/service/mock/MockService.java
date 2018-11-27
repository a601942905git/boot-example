package com.boot.example.core.service.mock;

import com.boot.example.core.entity.mock.Mock;
import com.boot.example.core.mapper.mock.MockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.core.service.mock.MockService
 *
 * @author lipeng
 * @dateTime 2018/11/27 上午11:16
 */
@Service
public class MockService {

    @Autowired
    private MockMapper mockMapper;


    public Mock getMock(Integer id) {
        return mockMapper.getMock(id);
    }
}
