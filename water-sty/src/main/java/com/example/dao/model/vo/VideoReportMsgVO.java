package com.example.dao.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VideoReportMsgVO {
        private String msgCode="report-code";
        private Long videoId;
        private String videoName;
        private String reportMessage;
        private Date cereteDate;
        private Date updateDate;
}
