package com.example.dao.mapper;

import com.example.dao.model.entity.FileInfoEntity;
import com.example.dao.model.entity.Video;
import com.example.dao.model.entity.VideoInfo;
import com.example.dao.model.vo.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: yin7331
 * Date: 2023/10/31 18:30
 * Describe:
 */
@Mapper
public interface MinioMapper {



    void saveVideo(Video video);

    void saveVideoInfo(VideoInfo videoInfo);

    List<VideoInfo> getVideoInfo(HashMap<String, Object> map);

    Integer getTotalInfo(@Param("area") Integer area);
}
