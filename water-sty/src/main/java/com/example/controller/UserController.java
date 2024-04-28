package com.example.controller;

import com.example.controller.Support.UserSupport;
import com.example.dao.model.dto.UserDto;
import com.example.dao.model.dto.UserVerifyDto;
import com.example.dao.model.entity.UserInfo;
import com.example.dao.model.entity.VideoInfo;
import com.example.service.PictureService;
import com.example.service.UserService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: yin7331
 * Date: 2023/10/23 0:43
 * Describe:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    ScrollingWebsocketController scrollingWebsocketController;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserSupport userSupport;
    @Autowired
    UserService userService;
    @Autowired
    PictureService pictureService;

    /**
     * 手机密码注册
     * @param user
     * @return
     */
    @PostMapping("/register-pw")
    public BaseResponse userRegisterByPW(@RequestBody UserDto user) {

        userService.userRegister(user);

        return ResultUtils.success();
    }

    /**
     * 手机验证码注册登录
     * @param user
     * @return
     */
    @PostMapping("/verification")
    public BaseResponse verification(@RequestBody UserVerifyDto user) throws Exception {

        Map map = userService.userRegisterByVerify(user);
        return ResultUtils.success(map);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @PostMapping("/getVerifyCode")
    public BaseResponse getVerifyCode(@RequestBody UserVerifyDto userVerifyDto){
        String code = userService.getVerifyCode(userVerifyDto.getPhoneNum());
        return ResultUtils.success(code);
    }

    /**
     * 手机密码登录
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/login-pw")
    public BaseResponse<Map<String, String>> loginForDtsByPW(
            @RequestBody UserDto user

    ) throws Exception {
        Map<String, String> map = userService.loginForDts(user);
        return ResultUtils.success(map);
    }



    @PostMapping("/logout")
    public BaseResponse<String> logout(){

        Long userId = userSupport.getCurrentUserId();
        userService.logout(  userId);
        return ResultUtils.success();
    }

    @PostMapping("/refresh-token")
    public BaseResponse<String> refreshAccessToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("refreshToken");
        String accessToken = userService.refreshAccessToken(refreshToken);
        return ResultUtils.success(accessToken);
    }

    @PostMapping("/userInfoPersonal")
    public BaseResponse userInfoPersonal(@RequestBody UserInfo userInfo){
        userService.saveUserInfoPersonal(userInfo);
        return ResultUtils.success();
    }

    @PostMapping("/getUserInfo")
    public BaseResponse getUserInfo(){
        UserInfo userInfo = userService.getUserInfo();
        return ResultUtils.success(userInfo);
    }

    @PostMapping("/picture/upload")
    public BaseResponse pictureLoad(HttpServletRequest req){
        userService.saveUserPicture(req);
        return ResultUtils.success();
    }
    @PostMapping("/getAuthorWorks/{userId}")
    public BaseResponse getAuthorWorks(@PathVariable("userId") Long userId){
        List<VideoInfo> authorWorks = userService.getAuthorWorks(userId);
        return ResultUtils.success(authorWorks);
    }

    @PostMapping("/getUserInfoByUserId/{userId}")
    public BaseResponse getUserInfoByUserId(@PathVariable("userId") Long userId){
        UserInfo authorWorks = userService.getUserInfoByUserId(userId);
        return ResultUtils.success(authorWorks);
    }
}
