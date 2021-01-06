package com.boot.example.mock;

import com.boot.example.core.mapper.mock.MockMapper;
import com.boot.example.core.service.mock.MockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


/**
 * com.boot.example.mock.UserControllerMockTest
 *
 * @author lipeng
 * @dateTime 2018/11/27 上午11:11
 */
public class UserServiceMockTest {

    @InjectMocks
    private MockService mockService;

    @Mock
    private MockMapper mockMapper;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mockMapper.getMock(any(Integer.class)))
                .thenReturn(com.boot.example.core.entity.mock.Mock.builder().id(1).name("model from mock").build());
    }

    @Test
    public void testMockService() throws Exception {
        Assertions.assertEquals(Integer.valueOf(1), mockService.getMock(1).getId());
    }
}
