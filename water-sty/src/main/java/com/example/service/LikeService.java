package com.example.service;

import com.example.dao.model.entity.UserLike;

/**
 * Author: yin7331
 * Date: 2023/12/29 23:31
 * Describe:
 */
public interface LikeService {
    void addLike(UserLike like);

    void cancelLike(UserLike like);

    UserLike getLikeStatus(UserLike like);
}
