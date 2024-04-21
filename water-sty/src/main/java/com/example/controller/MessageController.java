package com.example.controller;

import com.example.service.MessageService;
import com.example.service.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: yin7331
 * Date: 2023/11/14 6:46
 * Describe:
 */

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/getSystemMsg")
    public BaseResponse getSystemMsg(){
        messageService.getSystemMsg();
    }

}
