package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.ChatMapper;
import com.example.dao.mapper.UserMapper;
import com.example.dao.model.entity.ChatMsg;
import com.example.dao.model.entity.UserInfo;
import com.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatMapper chatMapper;
    @Autowired
    UserSupport userSupport;
    @Autowired
    UserMapper userMapper;

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1000*60*60, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<Runnable>());

    @Override
    public void syncSaveChat(ChatMsg chatMsg) {
        chatMapper.saveMsg(chatMsg);
    }

    @Override
    public void asyncSaveChat(ChatMsg chatMsg) {
         threadPoolExecutor.submit(()->{
            chatMapper.saveMsg(chatMsg);
        });

    }

    @Override
    public List<ChatMsg> getChatMsg(Long userId) {
        Long currentUserId = userSupport.getCurrentUserId();
        List<ChatMsg> msgs = chatMapper.getChatMsg(currentUserId,userId);
        return msgs;
    }

    @Override
    public List<UserInfo> getChatMsgPerson() {
        Long currentUserId = userSupport.getCurrentUserId();
        List<ChatMsg> chatMsgs = chatMapper.getChatMsgPerson(currentUserId);
        List<UserInfo> userInfos = new LinkedList<>();
        for (ChatMsg chatMsg : chatMsgs) {
            Long sendUserId = chatMsg.getSendUserId();
            UserInfo userInfoByUserId = userMapper.getUserInfoByUserId(sendUserId);
            userInfos.add(userInfoByUserId);
        }
        userInfos = userInfos.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(UserInfo::getUserId))), LinkedList::new));

        return userInfos;
    }
}
