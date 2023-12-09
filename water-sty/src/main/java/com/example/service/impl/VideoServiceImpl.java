package com.example.service.impl;

import com.example.dao.mapper.VideoMapper;
import com.example.dao.model.entity.Video;
import com.example.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: yin7331
 * Date: 2023/12/9 19:31
 * Describe:
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoMapper videoMapper;

    @Override
    public void savePicturePath(String picturePath,String md5) {
        videoMapper.savePicturePath(picturePath,md5);

    }

    @Override
    public void saveVideo(Video video) {
        videoMapper.saveVideo(video);
    }
}
