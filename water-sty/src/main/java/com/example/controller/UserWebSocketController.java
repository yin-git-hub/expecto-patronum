package com.example.controller;

import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;
import com.example.service.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: yin7331
 * Date: 2023/11/15 7:09
 * Describe:
 */
@ServerEndpoint(value = "/userWebSocket/{token}")
@Slf4j
@Component
public class UserWebSocketController {

    private Session session;

    private static AtomicInteger onlineCount = new AtomicInteger(0);
    private static ApplicationContext APPLICATION_CONTEXT;

    public static void setApplicationContext(ApplicationContext app) {
        APPLICATION_CONTEXT = app;
    }

    // 在线总人数
    private static ConcurrentHashMap<Long, UserWebSocketController> onlineMap =
            new ConcurrentHashMap();
    private Long userId = null;
    @OnOpen
    public void onOpen(Session session
                       ,@PathParam("token") String token
    ) {

        this.session = session;
        Long userId = TokenUtil.verifyToken(token);
        this.userId = userId;
        if (onlineMap.containsKey(this.userId)) {
            onlineMap.remove(this.userId);
            onlineMap.put(this.userId, this);
        } else {
            onlineMap.put(this.userId, this);
            onlineCount.getAndIncrement();
        }

        // todo 未进行测试登录后获取推送消息 获取视频推送
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) APPLICATION_CONTEXT.getBean("stringRedisTemplate");
        List<String> range = stringRedisTemplate.opsForList().range(String.valueOf(userId), 0, -1);

        range.stream().forEach((s)->{
            try {
                this.session.getBasicRemote().sendText(s);
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR,"推送信息失败");
            }
        });
    }

    @OnClose
    public void onClose() {

        onlineCount.decrementAndGet();

        onlineMap.remove(this.userId);
    }

    @OnMessage
    public void onMessage(String message) {

    }

    @OnError
    public void onError(Throwable error) {
         error.printStackTrace();
    }
    public Session getSession() {
        return this.session;
    }



    public ConcurrentHashMap getOnlineMap(){
        return onlineMap;
    }

}
