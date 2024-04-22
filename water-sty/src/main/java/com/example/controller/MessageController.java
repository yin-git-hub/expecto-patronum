package com.example.controller;

import com.example.service.MessageService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/getVideoReportMsg")
    public BaseResponse getVideoReportMsg(){
        List videoReportMsg = messageService.getVideoReportMsg();
        return ResultUtils.success(videoReportMsg);
    }

    @PostMapping("/getVideoUploadMsg")
    public BaseResponse getVideoUploadMsg(){
        List videoReportMsg = messageService.getVideoUploadMsg();
        return ResultUtils.success(videoReportMsg);
    }
    @PostMapping("/getVideoLikeMsg")
    public BaseResponse getVideoLikeMsg(){
        List videoReportMsg = messageService.getVideoLikeMsg();
        return ResultUtils.success(videoReportMsg);
    }

}
