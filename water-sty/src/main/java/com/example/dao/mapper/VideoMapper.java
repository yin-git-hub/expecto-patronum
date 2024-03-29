package com.example.dao.mapper;

import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.entity.Scrolling;
import com.example.dao.model.entity.Video;
import com.example.dao.model.entity.VideoInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: yin7331
 * Date: 2023/12/9 19:33
 * Describe:
 */
@Mapper
public interface VideoMapper {
    void savePicturePath(@Param("picturePath") String picturePath,@Param("md5") String md5);

    void saveVideo(Video video);
    Map<String, String> getVideoUrlFromVideo(@Param("id") Integer id);

    List<Scrolling> getScrolling(@Param("id") Integer id);

    VideoInfo getVideoInfoByVideoId(Long videoId);

    Video getVideoByVideoId(Long videoId);

    void deleteVideoInfoByVideoId(Long videoId);

    void deleteVideoByVideoId(Long videoId);

    List<VideoInfo> selectAll(SearchDto searchDto);

    List<VideoInfo> selectAllByContent(SearchDto searchDto);

    List<VideoInfo> selectAllSelf(Long userId);

    List<VideoInfo> searchCollection(List<Long> videoIdByUserId);
}
