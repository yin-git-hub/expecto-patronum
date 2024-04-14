package com.example.dao.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VideoRecord {
    private Long userId;
    private Long videoId;
    private String duration;
    private Date updateDate = new Date();

}
