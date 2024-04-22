package com.example.dao.model.vo;

import lombok.Data;

import java.util.Date;
@Data
public class VideoUploadMsgVO {
    private String msgCode="upload-code";
    private Long videoId;
    private Long videoReview;
    private String videoName;
    private Date cereteDate;
    private Date updateDate;
}
