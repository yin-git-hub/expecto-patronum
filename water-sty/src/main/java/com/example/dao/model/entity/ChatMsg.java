package com.example.dao.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMsg {
    Long id;
    Long sendUserId;
    Long acceptUserId;
    String message;
    Date createTime;
}
