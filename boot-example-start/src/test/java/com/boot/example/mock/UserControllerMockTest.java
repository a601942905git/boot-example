package com.boot.example.mock;

import com.boot.example.controller.mock.MockController;
import com.boot.example.core.service.mock.MockService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * com.boot.example.mock.UserControllerMockTest
 *
 * @author lipeng
 * @dateTime 2018/11/27 上午11:11
 */

public class UserControllerMockTest {

    @InjectMocks
    private MockController mockController;

    @Mock
    private MockService mockService;

    private MockMvc mockMvc;

    private String json = "{\"id\":1,\"name\":\"model from mock\"}";

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(mockController).build();

        when(mockService.getMock(any(Integer.class)))
                .thenReturn(com.boot.example.core.entity.mock.Mock.builder().id(1).name("model from mock").build());
    }

    @Test
    public void testMockController() throws Exception {
        mockMvc.perform(get("/mocks/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(json)));
    }
}
