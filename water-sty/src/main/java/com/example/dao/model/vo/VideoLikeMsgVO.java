package com.example.dao.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 给你视频点赞的人
 */
@Data
public class VideoLikeMsgVO {
    private String msgCode="like-code";
    private Long userId;
    private String nickname;
    private Long videoId;
    private String videoName;
    private Date cereteDate;
    private Date updateDate;
}
