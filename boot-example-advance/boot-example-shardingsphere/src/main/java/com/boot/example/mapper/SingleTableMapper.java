package com.boot.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.example.entity.SingleTable;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lipeng
 * &#064;date 2023/11/30 19:49:26
 */
@Mapper
public interface SingleTableMapper extends BaseMapper<SingleTable> {
}
