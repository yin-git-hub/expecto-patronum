package com.example.dao.mapper;

import com.example.dao.model.entity.CollectionGroup;
import com.example.dao.model.entity.CollectionGroupExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionGroupMapper {
    long countByExample(CollectionGroupExample example);

    int deleteByExample(CollectionGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CollectionGroup record);

    int insertSelective(CollectionGroup record);

    List<CollectionGroup> selectByExample(CollectionGroupExample example);

    CollectionGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CollectionGroup record, @Param("example") CollectionGroupExample example);

    int updateByExample(@Param("record") CollectionGroup record, @Param("example") CollectionGroupExample example);

    int updateByPrimaryKeySelective(CollectionGroup record);

    int updateByPrimaryKey(CollectionGroup record);

    List<CollectionGroup> selectByUserId(@Param("userId") Long userId);
}
