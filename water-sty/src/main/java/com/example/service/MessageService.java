package com.example.service;

import com.example.dao.model.entity.PushMessageEntity;
import com.example.dao.model.entity.VideoInfo;

/**
 * Author: yin7331
 * Date: 2023/11/14 6:47
 * Describe:
 */
public interface MessageService {
    void pushMessage(VideoInfo videoInfo);

    void sendVideoMessage(PushMessageEntity pushMessageEntity);
}
