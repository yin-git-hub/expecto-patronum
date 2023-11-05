package com.example.dao.mapper;

import com.example.dao.model.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author yin82
* @description 针对表【refresh_token(token刷新表)】的数据库操作Mapper
* @createDate 2023-10-25 02:38:28
* @Entity com.example.dao.entity.model.RefreshToken
*/
@Mapper
public interface RefreshTokenMapper  {

    void deleteRefreshTokenByUserId(Long userId);

    void addRefreshToken(@Param("refreshToken") String refreshToken,
                         @Param("userId") Long userId);

    RefreshToken getRefreshTokenByRefreshToken(String refreshToken);
}




