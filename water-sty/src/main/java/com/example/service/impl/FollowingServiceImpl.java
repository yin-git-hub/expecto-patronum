package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.FollowingGroupMapper;
import com.example.dao.mapper.FollowingMapper;
import com.example.dao.mapper.UserMapper;
import com.example.dao.model.entity.Following;
import com.example.dao.model.entity.FollowingGroup;
import com.example.dao.model.vo.UserVO;
import com.example.service.FollowingService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.ThrowUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/14 15:15
 * Describe:
 */
@Service
public class FollowingServiceImpl implements FollowingService {
    @Autowired
    FollowingMapper followingMapper;
    @Autowired
    UserSupport userSupport;

    @Autowired
    FollowingGroupMapper followingGroupMapper;

    @Autowired
    UserMapper userMapper;


    @Override
    public void saveFollowing(Following following) {
        ThrowUtils.throwIf(following==null, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(hasFollowing(following)==true,
                ErrorCode.PARAMS_ERROR,"已关注");
        followingMapper.insertSelective(following);
    }

    @Override
    public List<FollowingGroup> getFollowingGroup() {
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup>list = followingGroupMapper.selectByUserId(userId);
        return list;
    }

    @Override
    public void addFollowingGroup(FollowingGroup followingGroup) {
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        List<FollowingGroup> followingGroups = followingGroupMapper.selectByUserId(userId);

        ThrowUtils.throwIf(followingGroups.stream().anyMatch((s) -> {
            if (s.getGroupName().equals(followingGroup.getGroupName())) {
                return true;
            }
            return false;
        }),ErrorCode.OPERATION_ERROR,"已有此分组");
        followingGroupMapper.insertSelective(followingGroup);
    }

    @Override
    public List<UserVO> getFollowingByGroupId(Long groupId) {
        Long userId = userSupport.getCurrentUserId();
        List<Long> ups = followingMapper.selectUpIdByUserIdAndGroupId(userId, groupId);
        List<UserVO> userVOS = userMapper.getUsersByUserIds(ups);

        return userVOS;
    }

    @Override
    public List<UserVO> getFans() {
        Long userId = userSupport.getCurrentUserId();
        List<Long> users = followingMapper.selectFansByUserId(userId);
        List<UserVO> fans = userMapper.getUsersByUserIds(users);
        return fans;
    }

    @Override
    public Boolean hasFollowing(Following following) {
        Long userId = userSupport.getCurrentUserId();
        following.setUserId(userId);
        List<Following> hasFollowing = followingMapper.hasFollowing(following);
        if (hasFollowing==null||hasFollowing.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void deleteFollowing(String upId) {
        Long userId = userSupport.getCurrentUserId();
        followingMapper.deleteFollowing(userId,upId);
    }

    @Override
    @Transactional
    public void addFollowingToGroup(Following following) {
        List<Long> groupIds = following.getGroupIds();
        Long userId = userSupport.getCurrentUserId();
        List<Long> groupsByUserIdAndUpId = followingMapper.getGroupsByUserIdAndUpId(userId, following.getUpId());
        if (groupsByUserIdAndUpId!=null){
            for (Long aLong : groupsByUserIdAndUpId) {
                followingMapper.deleteGroupsByUserIdUpIdAndGroupId(userId, following.getUpId(),aLong);
            }
        }
        following.setUserId(userId);
        for (Long groupId : groupIds) {
            following.setGroupId(groupId);
            followingMapper.addFollowingToGroup(following);
        }

    }

    @Override
    public List getChoosedGroups(Following following) {
        Long userId = userSupport.getCurrentUserId();
        return followingMapper.getGroupsByUserIdAndUpId(userId, following.getUpId());
    }

    @Override
    public List<UserVO> getFollowings() {
        Long userId = userSupport.getCurrentUserId();
        List<Long> ups = followingMapper.selectUpIds(userId);
        List<UserVO> userVOS = userMapper.getUsersByUserIds(ups);

        return userVOS;
    }

    @Override
    public Integer getFollowingCount() {
        Long userId = userSupport.getCurrentUserId();
        List<Long> ups = followingMapper.selectUpIds(userId);
        if (ups==null||ups.isEmpty()){
            return 0;
        }
        return ups.size();
    }

    @Override
    public Integer getFollowingCountByGroup(Long groupId) {
        Long userId = userSupport.getCurrentUserId();
        List<Long> ups = followingMapper.selectUpIdByUserIdAndGroupId(userId, groupId);
        List<UserVO> userVOS = userMapper.getUsersByUserIds(ups);
        if (userVOS==null||userVOS.isEmpty()){
            return 0;
        }
        return userVOS.size();
    }

    @Override
    public void deleteFollowingGroup(String id) {
        Long userId = userSupport.getCurrentUserId();
        followingGroupMapper.deleteByPrimaryKey(Long.valueOf(id));
        followingMapper.updateGroupIdByGroupId(id,userId);
    }
}
