package com.example.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.model.entity.ChatMsg;
import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;
import com.example.service.exception.ThrowUtils;
import com.example.service.impl.ChatServiceImpl;
import com.example.service.utils.RabbitMQUtil;
import com.example.service.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
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
@ServerEndpoint(value = "/chat/{token}/{userId}")
@Slf4j
@Component
public class WebSocketChatController {

    private Session session;

    // 视频在线人数
    private static HashMap<String, Session> chatOnlineMap
            = new HashMap();
    public ScheduledExecutorService p = Executors.newScheduledThreadPool(1);

    private Long myUserId = null;
    private Long reUserId = null;

    // 通过 EndpointConfig 对象获取 HttpSession 中的属性

    @OnOpen
    public void onOpen(Session session
            , @PathParam("userId") String userId
            , @PathParam("token") String token
    ) {
        try {
            this.myUserId = TokenUtil.verifyToken(token);
            this.reUserId = Long.valueOf(userId);
        } catch (Exception e) {
            throw new BusinessException(5000, "用户未登录或过期");
        }

        this.session = session;
        chatOnlineMap.put(String.valueOf(this.myUserId),this.session);
        noticeOnlineCount();
    }

    @OnClose
    public void onClose() {
        chatOnlineMap.remove(myUserId);
    }

    @OnMessage
    public void onMessage(String message) {
        Session s = chatOnlineMap.get(reUserId);
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setMessage(message);
        chatMsg.setSendUserId(this.myUserId);
        chatMsg.setAcceptUserId(this.reUserId);
        ChatServiceImpl chatService= (ChatServiceImpl)ScrollingWebsocketController.APPLICATION_CONTEXT.getBean("chatServiceImpl");
        if(s==null){
            chatService.syncSaveChat(chatMsg);
        }else {
            try {
                s.getBasicRemote().sendText(message);
                chatService.asyncSaveChat(chatMsg);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.info("scrolling onError sessionId:{}", getSession().getId());
        error.printStackTrace();
    }

    public void sendCurrentPeopleOnline() {

        try {
            Session s = chatOnlineMap.get(this.reUserId);
            if (s==null) {
                this.session.getBasicRemote().sendText("false");
            }else{
                this.session.getBasicRemote().sendText("true");
            }

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
        Integer i = 1 * 1000;
        p.scheduleAtFixedRate(() -> {
            this.sendCurrentPeopleOnline();
        }, i, 1000, TimeUnit.MILLISECONDS);

    }

}


