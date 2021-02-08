package com.boot.example.mapper;

import com.boot.example.entity.LeafAlloc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (LeafAlloc)表数据库访问层
 *
 * @author lipeng
 * @since 2021-02-08 13:57:17
 */
@Mapper
public interface LeafAllocMapper {

    /**
     * 通过实体作为筛选条件查询
     *
     * @return 对象列表
     */
    List<LeafAlloc> list();

    LeafAlloc getByTag(@Param("bizTag") String bizTag);

    /**
     * 修改数据
     *
     * @param leafAlloc 实例对象
     * @return 受影响行数
     */
    int update(LeafAlloc leafAlloc);

    int updateMaxIdByTag(@Param("bizTag") String bizTag);

    void updateMaxIdAndCustomStepByTag(LeafAlloc leafAlloc);
}