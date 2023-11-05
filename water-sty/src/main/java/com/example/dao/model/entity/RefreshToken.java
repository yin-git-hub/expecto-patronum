package com.example.dao.model.entity;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * token刷新表
 * @TableName refresh_token
 */
 @Data
public class RefreshToken implements Serializable {
    /**
     * user id
     */
     private Long id;

    /**
     * user id
     */
    private String userId;

    /**
     * refresh token
     */
    private String refreshToken;

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
