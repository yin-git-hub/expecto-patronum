package com.example.controller;

import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: yin7331
 * Date: 2023/11/1 18:03
 * Describe:
 */
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //Logger：org.slf4j.Logger
    //LoggerFactory：org.slf4j.LoggerFactory
    @GetMapping("/test1")
    public BaseResponse test1(){
        String val = "wwwwqqqqqq";
        logger.info(val);
        return ResultUtils.success(val);
    }

     }
