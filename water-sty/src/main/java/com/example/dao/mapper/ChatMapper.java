package com.example.dao.mapper;

import com.example.dao.model.entity.ChatMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {
    public void saveMsg(ChatMsg chatMsg);

    List<ChatMsg> getChatMsg(@Param("currentUserId") Long currentUserId,@Param("userId") Long userId);

    List<ChatMsg> getChatMsgPerson(Long currentUserId);
}
