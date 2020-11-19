package com.boot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.service.DebugService
 *
 * @author lipeng
 * @date 2020/7/3 5:30 PM
 */
@Service
@Slf4j
public class DebugService {

    public String debug() {
        return "debug coding";
    }

}
