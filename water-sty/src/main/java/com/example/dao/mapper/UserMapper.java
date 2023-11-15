package com.example.dao.mapper;

import com.example.dao.model.entity.User;
import com.example.dao.model.entity.UserInfo;
import com.example.dao.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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


    List<UserVO> getUsersByUserIds(List<Long> ups);

    UserInfo getUserByUserId(Long userId);
}




