package com.example.service;

import com.example.dao.model.entity.Video;

/**
 * Author: yin7331
 * Date: 2023/12/9 19:31
 * Describe:
 */
public interface VideoService {
    void savePicturePath(String picturePath,String md5);

    void saveVideo(Video video);
}
