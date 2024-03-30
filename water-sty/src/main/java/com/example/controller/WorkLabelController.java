package com.example.controller;

import com.example.dao.model.entity.WorksLabel;
import com.example.service.WorkLabelService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2024/3/30 20:09
 * Describe:
 */
@RestController
@RequestMapping("/label")
public class WorkLabelController {

    @Autowired
    WorkLabelService workLabelService;

    @PostMapping("/getVideoLabel")
    public BaseResponse getVideoLabel(){

        List<WorksLabel> videoLabel = workLabelService.getVideoLabel();

        return ResultUtils.success(videoLabel);
    }
}
