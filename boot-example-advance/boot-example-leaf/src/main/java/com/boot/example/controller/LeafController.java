package com.boot.example.controller;

import com.boot.example.common.Result;
import com.boot.example.common.Status;
import com.boot.example.exception.LeafServerException;
import com.boot.example.exception.NoKeyException;
import com.boot.example.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.controller.LeafController
 *
 * @author lipeng
 * @date 2021/2/8 2:43 PM
 */
@RestController
public class LeafController {

    @Autowired
    private SegmentService segmentService;

    @GetMapping(value = "/api/segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return get(key, segmentService.get(key));
    }

    private String get(String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}
