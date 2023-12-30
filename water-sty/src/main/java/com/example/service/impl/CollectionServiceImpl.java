package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.CollectionGroupMapper;
import com.example.dao.mapper.CollectionMapper;
import com.example.dao.mapper.UserMapper;
import com.example.dao.model.entity.Collection;
import com.example.dao.model.entity.CollectionGroup;
import com.example.dao.model.vo.UserVO;
import com.example.service.CollectionService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.ThrowUtils;
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
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    UserSupport userSupport;

    @Autowired
    CollectionGroupMapper collectionGroupMapper;

    @Autowired
    UserMapper userMapper;


    @Override
    public void saveCollection(Collection collection) {
        ThrowUtils.throwIf(collection==null, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(hasCollection(collection)==true,
                ErrorCode.PARAMS_ERROR,"已关注");
        collectionMapper.insertSelective(collection);
    }

    @Override
    public List<CollectionGroup> getCollectionGroup() {
        Long userId = userSupport.getCurrentUserId();
        List<CollectionGroup>list = collectionGroupMapper.selectByUserId(userId);
        return list;
    }

    @Override
    public void addCollectionGroup(CollectionGroup collectionGroup) {
        Long userId = userSupport.getCurrentUserId();
        collectionGroup.setUserId(userId);
        List<CollectionGroup> collectionGroups = collectionGroupMapper.selectByUserId(userId);

        ThrowUtils.throwIf(collectionGroups.stream().anyMatch((s) -> {
            if (s.getGroupName().equals(collectionGroup.getGroupName())) {
                return true;
            }
            return false;
        }),ErrorCode.OPERATION_ERROR,"已有此分组");
        collectionGroupMapper.insertSelective(collectionGroup);
    }

    @Override
    public List<UserVO> getCollectionByGroupId(Long groupId) {
        Long userId = userSupport.getCurrentUserId();
        List<Long> videoIds = collectionMapper.selectVideoIdByUserIdAndGroupId(userId, groupId);
        List<UserVO> userVOS = userMapper.getUsersByUserIds(videoIds);

        return userVOS;
    }



    @Override
    public Boolean hasCollection(Collection collection) {
        Long userId = userSupport.getCurrentUserId();
        collection.setUserId(userId);
        List<Collection> hasCollection = collectionMapper.hasCollection(collection);
        if (hasCollection==null||hasCollection.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void deleteCollection(String videoId) {
        Long userId = userSupport.getCurrentUserId();
        collectionMapper.deleteCollection(userId,videoId);
    }

    @Override
    @Transactional
    public void addCollectionToGroup(Collection collection) {
        List<Long> groupIds = collection.getGroupIds();
        Long userId = userSupport.getCurrentUserId();
        List<Long> groupsByUserIdAndUpId = collectionMapper
                .getGroupsByUserIdAndUpId(userId, collection.getVideoId());

        if (groupsByUserIdAndUpId!=null){
            for (Long aLong : groupsByUserIdAndUpId) {
                collectionMapper
                        .deleteGroupsByUserIdUpIdAndGroupId(userId, collection.getVideoId(),aLong);
            }
        }
        collection.setUserId(userId);
        for (Long groupId : groupIds) {
            collection.setGroupId(groupId);
            collectionMapper.addCollectionToGroup(collection);
        }

    }

    @Override
    public List getChoosedGroups(Collection collection) {
        Long userId = userSupport.getCurrentUserId();
        return collectionMapper.getGroupsByUserIdAndUpId(userId, collection.getVideoId());
    }
}
