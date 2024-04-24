package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.ChatMapper;
import com.example.dao.model.entity.ChatMsg;
import com.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatMapper chatMapper;
    @Autowired
    UserSupport userSupport;

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
}
