package com.example.dao.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
 @Data
public class User implements Serializable {
    /**
     * user id
     */
     private Long id;

    /**
     * 电话号码
     */
    private String phoneNum;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

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
