package com.example.controller;

import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
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
    @GetMapping("/test1")
    public BaseResponse test1(){
        String val = "wwwwqqqqqq";
        return ResultUtils.success(val);
    }

@Autowired
    ScrollingService scrollingService;
    @PostMapping("/scrollingServiceTest")
        public BaseResponse scrollingServiceTest(@RequestBody Scrolling scrolling){
        scrollingService.saveScrollingToDB(scrolling);
        return ResultUtils.success();
        }}
