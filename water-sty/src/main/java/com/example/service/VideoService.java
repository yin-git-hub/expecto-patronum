package com.example.service;

import com.example.dao.model.entity.Scrolling;
import com.example.dao.model.entity.Video;
import com.example.dao.model.entity.WorksLabel;

import java.util.List;

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
}
