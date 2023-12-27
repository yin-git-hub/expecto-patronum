package com.example.service;

import com.example.dao.model.dto.UserDto;
import com.example.dao.model.entity.Following;
import com.example.dao.model.entity.FollowingGroup;
import com.example.dao.model.entity.User;
import com.example.dao.model.vo.FollowingVO;
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
}
