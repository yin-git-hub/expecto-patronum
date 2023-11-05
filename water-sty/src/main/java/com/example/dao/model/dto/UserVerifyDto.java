package com.example.dao.model.dto;

import lombok.Data;

/**
 * Author: yin7331
 * Date: 2023/10/27 2:48
 * Describe:
 */
@Data
public class UserVerifyDto {
    /**
     * 电话号码
     */
    private String phoneNum;
    /**
     * 验证码
     */
    String verifyCode;
}
