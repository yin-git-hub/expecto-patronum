package com.example.service;

import com.example.dao.model.entity.Collection;
import com.example.dao.model.entity.CollectionGroup;
import com.example.dao.model.vo.UserVO;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/14 15:10
 * Describe:
 */
public interface CollectionService {
    void saveCollection(Collection collection);


    List<CollectionGroup> getCollectionGroup();

    void addCollectionGroup(CollectionGroup collectionGroup);

    List<UserVO> getCollectionByGroupId(Long groupId);



    Boolean hasCollection(Collection collection);

    void deleteCollection(String videoId);

    void addCollectionToGroup(Collection collection);

    List getChoosedGroups(Collection collection);

    void deleteCollectionGroup(String id);
}
