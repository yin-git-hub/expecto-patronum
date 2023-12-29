package com.example.service;

import com.example.dao.model.entity.Like;

/**
 * Author: yin7331
 * Date: 2023/12/29 23:31
 * Describe:
 */
public interface LikeService {
    void addLike(Like like);

    void cancelLike(Like like);

    Like getLikeStatus(Like like);
}
