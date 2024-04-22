package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.example.controller.ScrollingWebsocketController;
import com.example.controller.Support.UserSupport;
import com.example.controller.UserWebSocketController;
import com.example.dao.mapper.UserMapper;
import com.example.dao.mapper.VideoMapper;
import com.example.dao.model.entity.PushMessageEntity;
import com.example.dao.model.entity.UserInfo;
import com.example.dao.model.entity.VideoInfo;
import com.example.dao.model.vo.UserVO;
import com.example.dao.model.vo.VideoLikeMsgVO;
import com.example.dao.model.vo.VideoReportMsgVO;
import com.example.dao.model.vo.VideoUploadMsgVO;
import com.example.service.FollowingService;
import com.example.service.MessageService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Author: yin7331
 * Date: 2023/11/14 6:47
 * Describe:
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    FollowingService followingService;

    @Autowired
    UserWebSocketController userWebsocketController;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    UserSupport userSupport;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void pushMessage(VideoInfo videoInfo) {
        PushMessageEntity pushMessageEntity = new PushMessageEntity();
        BeanUtil.copyProperties(videoInfo,pushMessageEntity);
        UserInfo userInfo = userMapper.getUserInfoByUserId(pushMessageEntity.getUserId());
        pushMessageEntity.setNickname(userInfo.getNickname());
        sendVideoMessage(pushMessageEntity);
    }

    @Override
    public void sendVideoMessage(PushMessageEntity pushMessageEntity) {
        List<UserVO> fans = followingService.getFans();
        ConcurrentHashMap<Long, ScrollingWebsocketController> onlineMap = userWebsocketController.getOnlineMap();
        fans.stream().forEach((s)->{
            Long userId = s.getUserId();
            if (onlineMap.containsKey(userId)) {
                ScrollingWebsocketController scrollingWebsocket = onlineMap.get(userId);
                Session session = scrollingWebsocket.getSession();
                try {
                    session.getBasicRemote().sendText(JSONUtil.toJsonStr(pushMessageEntity));
                } catch (IOException e) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR,"消息推送失败");
                }
            }else {
                stringRedisTemplate
                        .opsForList()
                        .rightPush(userId+"", JSONUtil.toJsonStr(pushMessageEntity))

                ;
            }
        });
    }

    @Override
    public List getVideoReportMsg() {
        Long currentUserId = userSupport.getCurrentUserId();
        List<VideoReportMsgVO> videoReport = videoMapper.getVideoReportMsg(currentUserId);
        return videoReport;
    }

    @Override
    public List getVideoUploadMsg() {
        Long currentUserId = userSupport.getCurrentUserId();
        List<VideoUploadMsgVO> videoReport = videoMapper.getVideoUploadMsg(currentUserId);
        return videoReport;
    }

    @Override
    public List getVideoLikeMsg() {
        Set<String> keys = redisTemplate.keys("video-like-*");
        if (keys.isEmpty()) {
            return null;
        }
        LinkedList<VideoLikeMsgVO> videoLikeMsgVOS = new LinkedList<>();
        for (String key : keys) {
            String videoId = key.replace("video-like-","");
            VideoInfo videoInfoByVideoId = videoMapper.getVideoInfoByVideoId(Long.valueOf(videoId));
            UserInfo userInfoByUserId = userMapper.getUserInfoByUserId(videoInfoByVideoId.getUserId());
            VideoLikeMsgVO videoLikeMsgVO = new VideoLikeMsgVO();
            videoLikeMsgVO.setNickname(userInfoByUserId.getNickname());
            BeanUtil.copyProperties(videoInfoByVideoId,videoLikeMsgVO);
            videoLikeMsgVOS.add(videoLikeMsgVO);
        }
        return videoLikeMsgVOS;
    }
}
