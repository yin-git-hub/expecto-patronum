package com.example.service.impl;
import java.util.*;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.RefreshTokenMapper;
import com.example.dao.mapper.UserMapper;
import com.example.dao.mapper.VideoMapper;
import com.example.dao.model.dto.UserDto;
import com.example.dao.model.dto.UserVerifyDto;
import com.example.dao.model.entity.RefreshToken;
import com.example.dao.model.entity.User;
import com.example.dao.model.entity.UserInfo;
import com.example.dao.model.entity.VideoInfo;
import com.example.service.UserService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;
import com.example.service.exception.ThrowUtils;
import com.example.service.utils.MD5Util;
import com.example.service.utils.TokenUtil;

import com.yin.minio.springminiostart.MinioTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
* @author yin82
* @description 针对表【t_user(用户表)】的数据库操作Service实现
* @createDate 2023-10-23 01:23:59
*/
@Service
public class UserServiceImpl
implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RefreshTokenMapper refreshTokenMapper;
    @Autowired
    StringRedisTemplate redisTemplate;
    // 注入TransactionTemplate对象
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    UserSupport userSupport;
    @Autowired
    private MinioTemplate minioTemplate;

    @Autowired
    VideoMapper videoMapper;

    @Override
    public void userRegister(UserDto user) {
        String phoneNum = user.getPhoneNum();
        String password = user.getPassword();

        ThrowUtils.throwIf(

                StringUtils.isAnyEmpty(password,String.valueOf(phoneNum)),
                ErrorCode.PARAMS_ERROR);
        String salt = String.valueOf(System.currentTimeMillis());
        String signPassword = MD5Util.sign(password, salt);
        User tUser = new User();
        tUser.setPhoneNum(phoneNum);
        tUser.setPassword(signPassword);
        tUser.setSalt(salt);
        userMapper.registerUser(tUser);

    }

    @Override
    public Map<String, String> loginForDts(UserDto userDto) throws Exception{
        ThrowUtils.throwIf(userDto==null, ErrorCode.NOT_FOUND_ERROR);
        String phoneNum = userDto.getPhoneNum();
        String password = userDto.getPassword();
        ThrowUtils.throwIf(StringUtils.isAnyEmpty(password,phoneNum),ErrorCode.NOT_FOUND_ERROR );

        User user = userMapper.getUserByPhoneNum(phoneNum);
        ThrowUtils.throwIf(user==null, ErrorCode.NOT_FOUND_ERROR,"用户不存在");

        String salt = user.getSalt();
        String rawPassword = user.getPassword();
        String signPassword = MD5Util.sign(password, salt);
        ThrowUtils.throwIf(!rawPassword.equals(signPassword), ErrorCode.PARAMS_ERROR,"密码错误");


        Long userId = user.getId();
        return getTokenMap(userId);
    }


    private Map<String, String> getTokenMap(Long userId) throws Exception {
        String accessToken = TokenUtil.generateToken(userId);
        String refreshToken = TokenUtil.generateRefreshToken(userId);
        //保存refresh token到数据库
        refreshTokenMapper.deleteRefreshTokenByUserId(userId);
        refreshTokenMapper.addRefreshToken(refreshToken, userId);
        Map<String, String> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        return result;
    }

    @Override
    public void logout(Long userId) {
        refreshTokenMapper.deleteRefreshTokenByUserId( userId);
    }

    @Override
    public String refreshAccessToken(String refreshToken) throws Exception {
        RefreshToken refreshTokentemp
                = refreshTokenMapper.getRefreshTokenByRefreshToken(refreshToken);
        if(refreshTokentemp == null){
            throw new BusinessException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }
        Long userId = Long.valueOf(refreshTokentemp.getUserId());
        return TokenUtil.generateToken(userId);
    }

    @Override
    public Map<String, Object> userRegisterByVerify(UserVerifyDto userVerifyDto) throws Exception {
        String phoneNum = userVerifyDto.getPhoneNum();
        String verifyCode = userVerifyDto.getVerifyCode();
        // TODO 添加图形验证
        // 查看手机号是否存在
        User userByPhoneNum = userMapper.getUserByPhoneNum(phoneNum);
        User userUsed =
                userByPhoneNum==null?new User():userByPhoneNum;

        // 验证码是否正确
        String code = redisTemplate.opsForValue().get(phoneNum + ":code");
        ThrowUtils.throwIf(code==null||!code.equals(verifyCode),
                ErrorCode.PARAMS_ERROR,"验证码错误");
        redisTemplate.delete(phoneNum + ":code");


        //   if 不存在
        if(userByPhoneNum==null){
            User user = new User();
            user.setPhoneNum(phoneNum);
            // 设置事务的传播行为为PROPAGATION_REQUIRED，表示如果当前没有事务，就新建一个事务，如果当前存在事务，就加入该事务
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            // 使用execute方法执行事务操作，传入一个TransactionCallback接口的实现类
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {

                        // 调用UserMapper的方法，执行数据库操作
                        userMapper.registerUser(user);
                        UserInfo userInfo = new UserInfo();
                        Long userId = user.getId();
                        userInfo.setUserId(userId);
                        userUsed.setId(userId);
                        String userName = "user".concat(String.valueOf(System.currentTimeMillis()));
                        userInfo.setNickname(userName);
                        userMapper.registerUserInfo(userInfo);
                    } catch (Exception e) {
                        // 如果发生异常，设置事务为回滚状态
                        status.setRollbackOnly();
                    }
                }
            });
        }

        return login(userUsed.getId());
    }

    @NotNull
    private Map<String, Object> login(Long userId) throws Exception {

        UserInfo userInfo = userMapper.getUserInfoByUserId(userId);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(userInfo);
        Map<String, String> tokenMap = getTokenMap(userId);
        stringObjectMap.put("refreshToken", tokenMap.get("refreshToken"));
        stringObjectMap.put("token", tokenMap.get("accessToken"));
        return stringObjectMap;
    }

    @Override
    public String getVerifyCode(String phoneNum) {
        // todo 改成手机信息验证码
        String code = RandomStringUtils.randomNumeric(6);
        redisTemplate.opsForValue().set(phoneNum+":code", code,
                30, TimeUnit.MINUTES
        );
        return code;
    }

    @Override
    public void saveUserInfoPersonal(UserInfo userInfo) {
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userMapper.saveUserInfoPersonal(userInfo);
    }

    @Override
    public UserInfo getUserInfo() {
        Long userId = userSupport.getCurrentUserId();
        UserInfo userInfo = userMapper.getUserInfoByUserId(userId);
        return userInfo;
    }

    @Override
    public void saveUserPicture(HttpServletRequest req) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

        // 获得文件分片数据
        // ((MultipartHttpServletRequest) req).getFile()
        MultipartFile file = multipartRequest.getFile("picture");

        // 上传过程中出现异常，状态码设置为50000
        ThrowUtils.throwIf(file == null, ErrorCode.OPERATION_ERROR);

        Long userId = userSupport.getCurrentUserId();
        String USER_AVATAR_BUCKET = "user-avatar-bucket";
        String userAvatarName = userId+""+ "image";
        // 创建文件桶
        if (!minioTemplate.bucketExists(USER_AVATAR_BUCKET)) {
            minioTemplate.makeBucket(USER_AVATAR_BUCKET);
        }
        try {
            // 上传文件
            minioTemplate.putChunkObject(file.getInputStream(), USER_AVATAR_BUCKET, userAvatarName);
            String picturePath = "http://localhost:7330/water-sty/picture/" + USER_AVATAR_BUCKET + "/" + userAvatarName;
            userMapper.savePicturePath(picturePath,userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<VideoInfo> getAuthorWorks(Long userId) {

        List<VideoInfo> videoInfoByUserId = videoMapper.getVideoInfoByUserId(userId);

        return videoInfoByUserId;
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        UserInfo userInfoByUserId = userMapper.getUserInfoByUserId(userId);
        return userInfoByUserId;
    }


}
