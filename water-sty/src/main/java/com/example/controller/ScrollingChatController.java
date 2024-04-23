package com.example.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;
import com.example.service.exception.ThrowUtils;
import com.example.service.utils.RabbitMQUtil;
import com.example.service.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Author: yin7331
 * Date: 2023/11/2 22:35
 * Describe: 弹幕 scrolling
 */
@ServerEndpoint(value = "/chat/{token}/{userId}/{content}")
@Slf4j
@Component
public class ScrollingChatController {

    private Session session;

    // 视频在线人数
    private static HashMap<String, List<Session>> currentMap
            = new HashMap();
    public static ScheduledExecutorService p = Executors.newScheduledThreadPool(1);

    private static ApplicationContext APPLICATION_CONTEXT;

    public static void setApplicationContext(ApplicationContext app) {
        APPLICATION_CONTEXT = app;
    }

    private Long userId = null;

    // 通过 EndpointConfig 对象获取 HttpSession 中的属性

    @OnOpen
    public void onOpen(Session session
            , @PathParam("content") String content
            , @PathParam("userId") String userId
            , @PathParam("token") String token
    ) {
        try {
            this.userId = TokenUtil.verifyToken(token);
        } catch (Exception e) {
            throw new BusinessException(5000, "用户未登录或过期");
        }

        this.session = session;




        noticeOnlineCount();

    }

    @OnClose
    public void onClose() {

        List<Session> currentList = null;
        synchronized (currentMap) {
            currentList.remove(this.session);

        }
    }

    @OnMessage
    public void onMessage(String message) {

    }

    @OnError
    public void onError(Throwable error) {
        log.info("scrolling onError sessionId:{}", getSession().getId());
        error.printStackTrace();
    }


    public void sendMessageCurrentVideo(Scrolling scrolling) {
        ThrowUtils.throwIf(scrolling == null, ErrorCode.PARAMS_ERROR);

        List<Session> currentList = currentMap.get(String.valueOf(scrolling.getVideoId()));

        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                try {
                    Session toSession = currentList.get(i);
                    String scrollingJsonStr = JSONUtil.toJsonStr(scrolling);
                    toSession.getBasicRemote().sendText(scrollingJsonStr);
                    log.info("发给 sessionID {},value:{}", toSession.getId(), scrolling.getScrollingContext());
                } catch (Exception e) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "弹幕发送失败");
                }
            }
        }


    }


    public void sendCurrentPeopleCount() {

        try {
            this.getSession().getBasicRemote().sendText( "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Session getSession() {
        return this.session;
    }


    // 或直接指定时间间隔，例如：5秒
    // @Scheduled(fixedRate=1000)
    // websocket 在启动类上添加 这个方法生效
    // @EnableScheduling
    private void noticeOnlineCount() {

        if (!currentMap.isEmpty()) {
            Integer i = 1 * 1000;
            p.scheduleAtFixedRate(() -> {
                this.sendCurrentPeopleCount();
            }, i, 1000, TimeUnit.MILLISECONDS);
        }
    }

}


