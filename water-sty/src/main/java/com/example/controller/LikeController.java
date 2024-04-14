package com.example.controller;

import com.example.dao.model.entity.UserLike;
import com.example.service.LikeService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: yin7331
 * Date: 2023/12/29 23:30
 * Describe:
 */
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    LikeService likeService;

    /**
     * 添加点赞
     * @param like
     * @return
     */
    @PostMapping("/addLike")
    public BaseResponse addLike(@RequestBody UserLike like){

        likeService.addLike(like);
        return ResultUtils.success();
    }

    /**
     * 取消点赞
     * @param like
     * @return
     */
    @PostMapping("/cancelLike")
    public BaseResponse cancelLike(@RequestBody UserLike like){

        likeService.cancelLike(like);
        return ResultUtils.success();
    }
    @PostMapping("/getLikeStatus")
    public BaseResponse getLikeStatus(@RequestBody UserLike like){
        UserLike likeStatus = likeService.getLikeStatus(like);
        return ResultUtils.success(likeStatus);
    }
}
