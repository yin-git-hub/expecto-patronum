package com.example.dao.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 弹幕
 * @TableName scrolling
 */
@Data
public class Scrolling implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long userId;

    /**
     *
     */
    private Long videoId;//reference
    /**
     * 弹幕内容
     */
    private String scrollingContext;
    /**
     * 弹幕出现的相对时间
     */
    private Long relativeTime;

    /**
     *
     */
    private Date createDate;

    /**
     *
     */
    private Date updateDate;



    private static final long serialVersionUID = 1L;
}
