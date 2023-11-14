package com.example.dao.mapper;

import com.example.dao.model.entity.FollowingGroup;
import com.example.dao.model.entity.FollowingGroupExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowingGroupMapper {
    long countByExample(FollowingGroupExample example);

    int deleteByExample(FollowingGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FollowingGroup record);

    int insertSelective(FollowingGroup record);

    List<FollowingGroup> selectByExample(FollowingGroupExample example);

    FollowingGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FollowingGroup record, @Param("example") FollowingGroupExample example);

    int updateByExample(@Param("record") FollowingGroup record, @Param("example") FollowingGroupExample example);

    int updateByPrimaryKeySelective(FollowingGroup record);

    int updateByPrimaryKey(FollowingGroup record);

    List<FollowingGroup> selectByUserId(Long userId);

}
