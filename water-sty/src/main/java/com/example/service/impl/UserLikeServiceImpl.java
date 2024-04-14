package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.LikeMapper;
import com.example.dao.mapper.VideoMapper;
import com.example.dao.model.entity.UserLike;
import com.example.dao.model.entity.VideoInfo;
import com.example.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserLikeServiceImpl implements UserLikeService {
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    UserSupport userSupport;
    @Autowired
    VideoMapper videoMapper;
    @Override
    public List<VideoInfo> getUserLikeVideoInfo() {
        Long currentUserId = userSupport.getCurrentUserId();
        List<UserLike> userLike = likeMapper.getUserLikeByUserId(currentUserId);
        LinkedList<VideoInfo> videoInfos = new LinkedList<>();
        for (UserLike like : userLike) {
            VideoInfo videoInfoByVideoId = videoMapper.getVideoInfoByVideoId(like.getVideoId());
            if(videoInfoByVideoId!=null){
                videoInfos.add(videoInfoByVideoId);
            }
        }
        return videoInfos;
    }
}
