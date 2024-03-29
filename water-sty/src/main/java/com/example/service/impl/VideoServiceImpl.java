package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.VideoMapper;
import com.example.dao.model.entity.Scrolling;
import com.example.dao.model.entity.Video;
import com.example.dao.model.entity.VideoInfo;
import com.example.service.MinioService;
import com.example.service.VideoService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.ThrowUtils;
import com.yin.minio.springminiostart.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Author: yin7331
 * Date: 2023/12/9 19:31
 * Describe:
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserSupport userSupport;
    @Autowired
    private MinioTemplate minioTemplate;

    @Override
    public void savePicturePath(String picturePath,String md5) {
        videoMapper.savePicturePath(picturePath,md5);

    }

    @Override
    public void saveVideo(Video video) {
        videoMapper.saveVideo(video);
    }

    @Value("${env.host}")
    String envHost;
    @Override
    public String getVideoUrl(Integer id) {
        Map<String,String> videoInfo = videoMapper.getVideoUrlFromVideo(id);
        String objectKey = videoInfo.get("object_key");
        String bucketName = videoInfo.get("bucket_name");
        String url = envHost+"/water-sty/video/play/"+bucketName+"/"+objectKey;
        return url;
    }

    @Override
    public List<Scrolling> getScrolling(Integer id) {
        List<Scrolling> scrolling = videoMapper.getScrolling(id);

        return scrolling;
    }

    @Override
    @Transactional
    public void deleteVideo(Long videoId) {
        VideoInfo videoInfo = videoMapper.getVideoInfoByVideoId(videoId);
        Video video = videoMapper.getVideoByVideoId(videoId);
        Long userId = userSupport.getCurrentUserId();
        ThrowUtils.throwIf(!userId.equals(videoInfo.getUserId()), ErrorCode.OPERATION_ERROR,"没有权限");
        videoMapper.deleteVideoInfoByVideoId(videoId);
        videoMapper.deleteVideoByVideoId(videoId);
        minioTemplate.deleteObject(video.getBucketName(),video.getObjectKey());
    }
}
