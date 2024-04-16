package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.ReportMapper;
import com.example.dao.mapper.VideoMapper;
import com.example.dao.model.entity.VideoInfo;
import com.example.dao.model.entity.VideoReport;
import com.example.service.ReportService;
import com.example.service.common.ErrorCode;
import com.example.service.constant.VideoConstant;
import com.example.service.exception.ThrowUtils;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    UserSupport userSupport;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    ReportMapper reportMapper;
    @Override
    public void addVideoReport(VideoReport videoReport) {
        Long currentUserId = userSupport.getCurrentUserId();
        VideoReport videoReportByUserIdAndVideo = reportMapper.getVideoReportByUserIdAndVideo(currentUserId, videoReport.getVideoId());
        ThrowUtils.throwIf(videoReportByUserIdAndVideo!=null
        , ErrorCode.OPERATION_ERROR,"请勿重复举报！");
        videoReport.setReportingUserId(currentUserId);
        VideoInfo videoInfoByVideoId = videoMapper.getVideoInfoByVideoId(videoReport.getVideoId());
        videoReport.setReportedUserId(videoInfoByVideoId.getUserId());
        videoReport.setReportStatus(VideoConstant.REPORT_REPORTED);
        BeanUtil.copyProperties(videoInfoByVideoId,videoReport);
        reportMapper.addVideoReport(videoReport);
    }
}
