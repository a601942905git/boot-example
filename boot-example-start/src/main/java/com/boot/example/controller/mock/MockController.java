package com.boot.example.controller.mock;

import com.boot.example.core.entity.mock.Mock;
import com.boot.example.core.service.mock.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return mockService.getMock(id);
    }
}
