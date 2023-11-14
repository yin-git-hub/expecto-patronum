package com.example.service.impl;

import com.example.dao.mapper.UserMapper;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: yin7331
 * Date: 2023/11/14 6:47
 * Describe:
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    UserMapper userMapper;
    // @Override
    // public void pushMessage(Long userId){
    //
    //
    //
    // }
}
