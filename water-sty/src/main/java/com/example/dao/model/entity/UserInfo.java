package com.example.dao.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName user_info
 */
 @Data
public class UserInfo implements Serializable {
    /**
     *
     */
     private Integer id;

    /**
     *
     */
    private Long userId;

    /**
     * 头像
     */
    private String image;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 个性签名
     */
    private String signature;

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
