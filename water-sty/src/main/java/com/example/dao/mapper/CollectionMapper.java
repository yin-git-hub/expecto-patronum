package com.example.dao.mapper;

import com.example.dao.model.entity.Collection;
import com.example.dao.model.entity.CollectionExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionMapper {
    long countByExample(CollectionExample example);

    int deleteByExample(CollectionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Collection record);

    int insertSelective(Collection record);

    List<Collection> selectByExample(CollectionExample example);

    Collection selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Collection record, @Param("example") CollectionExample example);

    int updateByExample(@Param("record") Collection record, @Param("example") CollectionExample example);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);

    List<Long> getGroupsByUserIdAndUpId(@Param("userId") Long userId,@Param("videoId") Long videoId);

    void deleteGroupsByUserIdUpIdAndGroupId(@Param("userId") Long userId
            ,@Param("videoId") Long videoId
            ,@Param("groupId") Long groupId);

    void addCollectionToGroup(Collection collection);

    void deleteCollection(@Param("userId") Long userId
            ,@Param("videoId") String videoId);

    List<Collection> hasCollection(Collection collection);

    List<Long> selectVideoIdByUserIdAndGroupId(@Param("userId")Long userId, @Param("groupId")Long groupId);

    void updateGroupIdByGroupId(@Param("id") String id,@Param("userId")Long userId);
}
