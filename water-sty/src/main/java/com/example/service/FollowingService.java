package com.example.service;

import com.example.dao.model.entity.Following;
import com.example.dao.model.entity.FollowingGroup;
import com.example.dao.model.vo.UserVO;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/14 15:10
 * Describe:
 */
public interface FollowingService {
    void saveFollowing(Following following);


    List<FollowingGroup> getFollowingGroup();

    void addFollowingGroup(FollowingGroup followingGroup);

    List<UserVO> getFollowingByGroupId(Long groupId);

    List<UserVO> getFans();

    Boolean hasFollowing(Following following);

    void deleteFollowing(String upId);

    void addFollowingToGroup(Following following);

    List getChoosedGroups(Following following);

    List<UserVO> getFollowings();

    Integer getFollowingCount();

    Integer getFollowingCountByGroup(Long userId);

    void deleteFollowingGroup(String id);
}
