package com.boot.example.service;

import com.boot.example.common.Result;

/**
 * com.boot.example.service.IdGenerate
 *
 * @author lipeng
 * @date 2021/2/8 2:00 PM
 */
public interface IdGenerate {

    Result get(String key);

    boolean init();
}
