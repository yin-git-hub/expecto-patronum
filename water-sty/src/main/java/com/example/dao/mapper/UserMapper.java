package com.example.dao.mapper;

import com.example.dao.model.entity.User;
 import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author yin82
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-10-23 22:33:22
* @Entity com.example.model.entity.UserDto
*/

@Mapper
public interface UserMapper   {

    Integer registerUser(User tUser);

    User getUserByPhoneNum(String phoneNum);


 }




