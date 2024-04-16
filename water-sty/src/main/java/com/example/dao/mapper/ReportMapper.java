package com.example.dao.mapper;

import com.example.dao.model.entity.VideoReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportMapper {

    void addVideoReport(VideoReport videoReport);

    VideoReport getVideoReportByUserIdAndVideo(@Param("currentUserId") Long currentUserId
            , @Param("videoId") Long videoId);
}
