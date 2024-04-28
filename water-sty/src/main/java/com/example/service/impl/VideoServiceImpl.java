package com.example.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.controller.ScrollingWebsocketController;
import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.VideoMapper;
import com.example.dao.model.entity.Scrolling;
import com.example.dao.model.entity.Video;
import com.example.dao.model.entity.VideoInfo;
import com.example.dao.model.entity.VideoRecord;
import com.example.service.VideoService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.ThrowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    StringRedisTemplate redisTemplate;

    @Autowired
    UserSupport userSupport;


    @Override
    public void savePicturePath(String picturePath, String md5) {
        videoMapper.savePicturePath(picturePath, md5);

    }

    // 前缀
    String videoRecordPrefix = "video-record-";

    @Override
    public void saveVideo(Video video) {
        videoMapper.saveVideo(video);
    }

    @Override
    public String getVideoUrl(Integer id) {
        Map<String, String> videoInfo = videoMapper.getVideoUrlFromVideo(id);
        String objectKey = videoInfo.get("object_key");
        String bucketName = videoInfo.get("bucket_name");
        String url = "http://localhost:7330/water-sty/video/play/" + bucketName + "/" + objectKey;
        return url;
    }

    @Override
    public List<Scrolling> getScrolling(Integer id) {
        List<Scrolling> scrolling = videoMapper.getScrolling(id);

        return scrolling;
    }

    @Override
    public void delVideo(Long videoId) {
        videoMapper.delVideo(videoId);
    }

    @Override
    public void addVideoRecord(VideoRecord videoRecord) {

        Long currentUserId = userSupport.getCurrentUserId();
        videoRecord.setUserId(currentUserId);
        String videoRecordKey = videoRecordPrefix + currentUserId + "-" + videoRecord.getVideoId();
        redisTemplate.opsForValue().set(videoRecordKey, JSONUtil.toJsonStr(videoRecord));

    }

    @Override
    public List<VideoInfo> getVideoRecord() {
        Long currentUserId = userSupport.getCurrentUserId();
        LinkedList<VideoInfo> videoInfos = new LinkedList<>();
        //重复
        HashSet<String> repeatKey = new HashSet<>();
        Set<String> keys = redisTemplate.keys(videoRecordPrefix+ currentUserId + "*");
        if (keys!=null&&!keys.isEmpty()){
            for (String key : keys) {
                String s = redisTemplate.opsForValue().get(key);
                VideoRecord videoRecord =  JSONUtil.toBean(s,VideoRecord.class);
                VideoInfo videoInfoByVideoId = videoMapper.getVideoInfoByVideoId(videoRecord.getVideoId());
                videoInfos.add(videoInfoByVideoId);
                repeatKey.add(videoRecord.getUserId()+videoRecord.getVideoId()+"");
            }
        }

        List<VideoRecord> videoRecord = videoMapper.getVideoRecordByUserId(currentUserId);
        for (VideoRecord record : videoRecord) {
             VideoInfo videoInfoByVideoId = videoMapper.getVideoInfoByVideoId(record.getVideoId());
            int size = repeatKey.size();
            repeatKey.add(record.getUserId()+record.getVideoId()+"");
            if (size!=repeatKey.size()&&videoInfoByVideoId!=null){
                videoInfos.add(videoInfoByVideoId);
            }
        }
        return videoInfos;
    }

    @Override
    public List<VideoInfo> getVideoInfo(String ortherSort, Long id) {
        List<VideoInfo> videoInfos = null;
        Long currentUserId = userSupport.getCurrentUserId();
        if (ortherSort.equals("status")) {
            videoInfos =  videoMapper.getVideoInfoByUserIdAndVideoStatus(currentUserId,id);
        }else if(ortherSort.equals("review")){
            videoInfos =  videoMapper.getVideoInfoByUserIdAndVideoReview(currentUserId,id);
        }
        return videoInfos;
    }

    @Override
    public String getVideoUrlById(Long videoId) {
        Map<String, String> videoInfo = videoMapper.getVideoUrlFromVideo(Math.toIntExact(videoId));
        String objectKey = videoInfo.get("object_key");
        String bucketName = videoInfo.get("bucket_name");
        String url = "http://localhost:7330/water-sty/video/play/" + bucketName + "/" + objectKey;
        return url;
    }

    @Scheduled(fixedRate = 1000)
    public void addVideoRecordToDB() {

        Set<String> keys = redisTemplate.keys(videoRecordPrefix + "*");
        for (String key : keys) {
            String s = redisTemplate.opsForValue().get(key);
            VideoRecord videoRecord =  JSONUtil.toBean(s,VideoRecord.class);
            List<VideoRecord> videoRecords = videoMapper.getVideoRecord(videoRecord);
            if (videoRecords==null||videoRecords.isEmpty()) {

                videoMapper.addVideoRecordToDB(videoRecord);
            }else {
                videoMapper.updateVideoRecordToDB(videoRecord);
            }
            redisTemplate.delete(key);
        }
    }


}
