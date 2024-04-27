package com.example.controller;

import com.example.dao.model.entity.ChatMsg;
import com.example.dao.model.entity.UserInfo;
import com.example.service.ChatService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    @PostMapping("/getChatMsg/{userId}")
    public BaseResponse getChatMsg(@PathVariable("userId") Long userId){
        List<Map> msgs = chatService.getChatMsg(userId);
        return ResultUtils.success(msgs);
    }
    @PostMapping("/getChatMsgPerson")
    public BaseResponse getChatMsgPerson(){
        List<UserInfo> msgs = chatService.getChatMsgPerson();
        return ResultUtils.success(msgs);
    }
}
