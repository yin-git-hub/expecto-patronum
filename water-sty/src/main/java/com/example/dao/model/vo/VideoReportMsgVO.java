package com.example.dao.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VideoReportMsgVO {

        private Long videoId;
        private String videoName;
        private Date cereteDate;
        private Date updateDate;
}
