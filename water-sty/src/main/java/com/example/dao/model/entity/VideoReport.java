package com.example.dao.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VideoReport {
    Integer id;
    private Long reportedUserId;
    private Long reportingUserId;
    private Long videoId;
    private String videoName;
    private String videoSummary;
    private Long videoSize;
    private String videoCover;
    private Integer worksLabelId;
    private Integer reportStatus;
    private String reportMessage;
    private Date cereteDate;
    private Date updateDate;

}
