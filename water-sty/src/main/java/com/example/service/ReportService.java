package com.example.service;

import com.example.dao.model.entity.VideoReport;

import java.util.List;

public interface ReportService {
    void addVideoReport(VideoReport videoReport);

    List getVideoReportReturn();
}
