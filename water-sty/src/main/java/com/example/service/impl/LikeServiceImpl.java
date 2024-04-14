package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.LikeMapper;
import com.example.dao.model.entity.UserLike;
import com.example.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: yin7331
 * Date: 2023/12/29 23:32
 * Describe:
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeMapper likeMapper;

    @Autowired
    UserSupport userSupport;
    @Override
    public void addLike(UserLike like) {
        Long userId = userSupport.getCurrentUserId();
        like.setUserId(userId);
        likeMapper.insertSelective(like);
    }

    @Override
    public void cancelLike(UserLike like) {
        Long userId = userSupport.getCurrentUserId();
        like.setUserId(userId);
        likeMapper.deleteByLike(like);
    }

    @Override
    public UserLike getLikeStatus(UserLike like) {
        Long userId = userSupport.getCurrentUserId();
        like.setUserId(userId);
        return likeMapper.selectByLike(like);
    }
}
