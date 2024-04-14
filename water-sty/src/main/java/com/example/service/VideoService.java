package com.example.service;

import com.example.dao.model.entity.*;
import com.example.dao.model.vo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Author: yin7331
 * Date: 2023/12/9 19:31
 * Describe:
 */
public interface VideoService {
    void savePicturePath(String picturePath,String md5);
    void saveVideo(Video video);

    String getVideoUrl(Integer id);

    List<Scrolling> getScrolling(Integer id);


    void delVideo(Long videoId);

    void addVideoRecord(VideoRecord videoRecord);

    List<VideoInfo> getVideoRecord();
}
