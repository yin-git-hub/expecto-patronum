package com.example.dao.mapper;

import com.example.dao.model.entity.FileInfoEntity;
import com.example.dao.model.vo.PageResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author: yin7331
 * Date: 2023/10/31 18:30
 * Describe:
 */
@Mapper
public interface MinioMapper {
    Integer saveFileInfo(FileInfoEntity fileInfoEntity);

    List<FileInfoEntity> getVideoInfo(Map map);

    Integer getTotalInfo(Integer area);
}
