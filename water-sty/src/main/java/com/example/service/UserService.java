package com.example.service;



import com.example.dao.model.dto.UserDto;
import com.example.dao.model.dto.UserVerifyDto;
import com.example.dao.model.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @author yin82
* @description 针对表【t_user(用户表)】的数据库操作Service
* @createDate 2023-10-23 01:23:59
*/

public interface UserService   {

     void userRegister(UserDto user);

     Map<String, String> loginForDts(UserDto user) throws Exception;

     void logout( Long userId);

     String refreshAccessToken(String refreshToken) throws Exception;

    Map userRegisterByVerify(UserVerifyDto user) throws Exception;

    String getVerifyCode(String phoneNum);

    void saveUserInfoPersonal(UserInfo userInfo);

    UserInfo getUserInfo();


    void saveUserPicture(HttpServletRequest req);
}
