package com.example.dao.mapper;

import com.example.dao.model.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author: yin7331
 * Date: 2023/12/9 19:33
 * Describe:
 */
@Mapper
public interface VideoMapper {
    void savePicturePath(@Param("picturePath") String picturePath,@Param("md5") String md5);

    void saveVideo(Video video);
}
