package com.boot.example.controller.mock;

import com.boot.example.core.entity.mock.Mock;
import com.boot.example.core.service.mock.MockService;
import com.boot.example.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * com.boot.example.controller.mock.MockController
 *
 * @author lipeng
 * @dateTime 2018/11/27 上午11:15
 */
@RestController
@RequestMapping("/mocks/")
public class MockController {

    @Autowired
    private MockService mockService;

    @RequestMapping("/{id}")
    public Mock getMock(@PathVariable Integer id) {
        if (Objects.equals(id, 0)) {
            throw new CustomerException("10001", "抛出自定义异常");
        }
        return mockService.getMock(id);
    }
}
