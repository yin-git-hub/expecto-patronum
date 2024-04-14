package com.example.controller;

import com.example.dao.model.entity.VideoInfo;
import com.example.service.UserLikeService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/like")
public class UserLikeController {
    @Autowired
    UserLikeService userLikeService;

    @PostMapping("/getUserLikeVideoInfo")
    public BaseResponse getUserLikeVideoInfo(){
        List<VideoInfo> videoInfos = userLikeService.getUserLikeVideoInfo();
        return ResultUtils.success(videoInfos);
    }
}
