package com.boot.example.service;

import com.boot.example.entity.LeafAlloc;
import com.boot.example.mapper.LeafAllocMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * com.boot.example.service.LeafAllocService
 *
 * @author lipeng
 * @date 2021/2/8 2:23 PM
 */
@Service
public class LeafAllocService {

    @Autowired
    private LeafAllocMapper leafAllocMapper;

    public List<String> listAllTag() {
        List<LeafAlloc> leafAllocList = leafAllocMapper.list();
        if (CollectionUtils.isEmpty(leafAllocList)) {
            return null;
        }

        return leafAllocList.stream().map(LeafAlloc::getBizTag).collect(Collectors.toList());
    }

    public LeafAlloc updateMaxIdAndGetLeafAlloc(String bizTag) {
        leafAllocMapper.updateMaxIdByTag(bizTag);
        return leafAllocMapper.getByTag(bizTag);
    }

    public LeafAlloc updateMaxIdAndCustomStepByTag(LeafAlloc leafAlloc) {
        leafAllocMapper.updateMaxIdAndCustomStepByTag(leafAlloc);
        return leafAllocMapper.getByTag(leafAlloc.getBizTag());
    }
}
