package com.example.dao.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: yin7331
 * Date: 2023/11/14 21:35
 * Describe:
 */
@Data
public class UserVO implements Serializable {
    /**
     * user id
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

    private Long userCount;

}
