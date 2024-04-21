package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.example.controller.ScrollingWebsocketController;
import com.example.controller.UserWebSocketController;
import com.example.dao.mapper.UserMapper;
import com.example.dao.model.entity.PushMessageEntity;
import com.example.dao.model.entity.UserInfo;
import com.example.dao.model.entity.VideoInfo;
import com.example.dao.model.vo.UserVO;
import com.example.service.FollowingService;
import com.example.service.MessageService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
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
    public List getSystemMsg() {
        return null;
    }
}
