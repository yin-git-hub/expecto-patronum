package com.example.dao.mapper;

import com.example.dao.model.entity.Following;
import com.example.dao.model.entity.FollowingExample;
import java.util.List;

import com.example.dao.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowingMapper {
    long countByExample(FollowingExample example);

    int deleteByExample(FollowingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Following record);

    int insertSelective(Following record);

    List<Following> selectByExample(FollowingExample example);

    Following selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Following record, @Param("example") FollowingExample example);

    int updateByExample(@Param("record") Following record, @Param("example") FollowingExample example);

    int updateByPrimaryKeySelective(Following record);

    int updateByPrimaryKey(Following record);

    List<Following> selectByUserId(@Param("userId") Long userId);

    List<Long> selectUpIdByUserIdAndGroupId(@Param("userId") Long userId,@Param("groupId") Long groupId);

    List<Long> selectFansByUserId(Long userId);

    List<Following> hasFollowing(Following following);

    void deleteFollowing(@Param("userId") Long userId,
                         @Param("upId") String upId);

    void addFollowingToGroup(Following following);

    /**
     * 根据Long userId, Long upId获取所有分组groups
     * @param userId
     * @param upId
     * @return
     */
    List<Long> getGroupsByUserIdAndUpId(@Param("userId") Long userId,@Param("upId")  Long upId);

    void deleteGroupsByUserIdUpIdAndGroupId(
            @Param("userId") Long userId
            ,@Param("upId")Long upId
            ,@Param("groupId") Long groupId);
}
