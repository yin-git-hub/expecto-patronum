package com.example.service;

import com.example.dao.model.entity.ChatMsg;
import com.example.dao.model.entity.UserInfo;

import java.util.List;

public interface ChatService {
    public void syncSaveChat(ChatMsg chatMsg);
    public void asyncSaveChat(ChatMsg chatMsg);

    List<ChatMsg> getChatMsg(Long userId);

    List<UserInfo> getChatMsgPerson();
}
