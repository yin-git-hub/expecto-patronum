package com.example.dao.mapper;

import com.example.dao.model.entity.UserLike;
import com.example.dao.model.entity.LikeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    long countByExample(LikeExample example);

    int deleteByExample(LikeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserLike record);

    int insertSelective(UserLike record);

    List<UserLike> selectByExample(LikeExample example);

    UserLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserLike record, @Param("example") LikeExample example);

    int updateByExample(@Param("record") UserLike record, @Param("example") LikeExample example);

    int updateByPrimaryKeySelective(UserLike record);

    int updateByPrimaryKey(UserLike record);

    void deleteByLike(UserLike like);

    UserLike selectByLike(UserLike like);

    List<UserLike> getUserLikeByUserId(Long currentUserId);
}
