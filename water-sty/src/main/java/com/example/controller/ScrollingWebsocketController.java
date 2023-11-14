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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Author: yin7331
 * Date: 2023/11/2 22:35
 * Describe: 弹幕 scrolling
 */
@ServerEndpoint(value = "/scrolling/{token}/{videoId}")
@Slf4j
@Component
public class ScrollingWebsocketController {

    private Session session;

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    // 在线总人数
    private static ConcurrentHashMap<Long, ScrollingWebsocketController> onlineMap =
            new ConcurrentHashMap();

    // 视频在线人数
    private static HashMap<String, List<Session>> currentMap
            = new HashMap();


    private static ApplicationContext APPLICATION_CONTEXT;

    public static void setApplicationContext(ApplicationContext app) {
        APPLICATION_CONTEXT = app;
    }

    private Long userId = null;
    private String videoId = null;


    @OnOpen
    public void onOpen(Session session
            , @PathParam("token") String token
            , @PathParam("videoId") String videoId) {


        try {

            this.userId = TokenUtil.verifyToken(token);

        } catch (Exception e) {
        }

        this.session = session;
        String sessionId = session.getId();
        if (onlineMap.containsKey(this.userId)) {
            onlineMap.remove(this.userId);
            onlineMap.put(this.userId, this);

        } else {
            onlineMap.put(this.userId, this);
            onlineCount.getAndIncrement();
        }


        if ((this.videoId = videoId) == null) {
            return;
        }

        if (currentMap.containsKey(videoId)) {
            synchronized (currentMap) {
                List<Session> currentList = currentMap.get(videoId);
                currentList.add(this.session);
                currentMap.put(videoId, currentList);
            }
        } else {
            synchronized (currentMap) {
                if (!currentMap.containsKey(videoId)) {

                    List<Session> currentList = new LinkedList<>();
                    currentList.add(this.session);
                    currentMap.put(videoId, currentList);
                }

            }
        }
        noticeOnlineCount();
        log.info("scrolling onOpen sessionId:{} 当前人数{}", sessionId, onlineCount.get());

    }

    @OnClose
    public void onClose() {

        onlineCount.decrementAndGet();

        onlineMap.remove(this.userId);

        List<Session> currentList = null;
        synchronized (currentMap) {
            currentList = currentMap.get(this.videoId);
            currentList.remove(this.session);

        }
        if (currentList.isEmpty()) {
            currentMap.remove(videoId);
        }
     }

    @OnMessage
    public void onMessage(String message) {

        // todo 发弹幕必须登录，测试方便先注释掉
        // ThrowUtils.throwIf(this.userId==null, ErrorCode.NOT_LOGIN_ERROR);
        log.info("sessionId {} 发来消息{}", session.getId(), message);
        Scrolling scrolling = JSONObject.parseObject(message, Scrolling.class);
        ThrowUtils.throwIf(scrolling == null, ErrorCode.PARAMS_ERROR);
        scrolling.setUserId(this.userId);
        scrolling.setVideoId(Long.valueOf(this.videoId));
        ScrollingService scrollingService = (ScrollingService) APPLICATION_CONTEXT.getBean("scrollingServiceImpl");
        RabbitTemplate rabbitTemplate = (RabbitTemplate) APPLICATION_CONTEXT.getBean("rabbitTemplate");

        RabbitMQUtil.asyncSendMessage(scrolling, rabbitTemplate);

        scrollingService.saveScroller(scrolling);
        scrollingService.saveScrollingToDB(scrolling);
    }

    @OnError
    public void onError(Throwable error) {
        log.info("scrolling onError sessionId:{}", getSession().getId());
        error.printStackTrace();
    }


    public void sendMessageCurrentVideo(Scrolling scrolling) {
        ThrowUtils.throwIf(scrolling == null, ErrorCode.PARAMS_ERROR);

        List<Session> currentList = currentMap.get(String.valueOf(scrolling.getVideoId()));

        ThrowUtils.throwIf(currentList == null, ErrorCode.OPERATION_ERROR);
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


    public void sendCurrentPeopleCount() {
        List<Session> currentList = currentMap.get(this.videoId);
        try {
            this.getSession().getBasicRemote().sendText(String.valueOf(currentList.size()));
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
            ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
            Integer i = 1 * 1000;
            pool.scheduleAtFixedRate(() -> {
                this.sendCurrentPeopleCount();
            }, i, 1000, TimeUnit.MILLISECONDS);
        }
    }

}


