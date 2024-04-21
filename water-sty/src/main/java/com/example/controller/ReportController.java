package com.example.controller;

import com.example.dao.model.entity.VideoReport;
import com.example.service.ReportService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("/addVideoReport")
    public BaseResponse addVideoReport(@RequestBody VideoReport videoReport){
        reportService.addVideoReport(videoReport);
        return ResultUtils.success();
    }

    /**
     * 获取举报反馈
     * @return
     */
    @PostMapping("/getVideoReportReturn")
    public BaseResponse getVideoReportReturn(){
        reportService.getVideoReportReturn();
        return ResultUtils.success();
    }

}
