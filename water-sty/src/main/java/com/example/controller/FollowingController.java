package com.example.controller;

import com.example.controller.Support.UserSupport;
import com.example.dao.model.entity.Following;
import com.example.dao.model.entity.FollowingGroup;
import com.example.dao.model.vo.FollowingVO;
import com.example.dao.model.vo.UserVO;
import com.example.service.FollowingService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/14 15:06
 * Describe:
 */
@RestController
@RequestMapping("/following")
public class FollowingController {

    @Autowired
    FollowingService followingService;


    @PostMapping("/addFollowingGroup")
    public BaseResponse addFollowingGroup(@RequestBody FollowingGroup followingGroup){
        followingService.addFollowingGroup(followingGroup);
        return ResultUtils.success();
    }
    @PostMapping("/addFollowing")
    public BaseResponse addFollowing(@RequestBody Following following){
        followingService.saveFollowing(following);
        return ResultUtils.success();
    }

    /**
     * 是否存在该关注
     * @param following
     * @return
     */

    @ApiOperation("hasFollowing 是否存在该关注")
    @PostMapping("/hasFollowing")
    public BaseResponse hasFollowing(@RequestBody Following following){
        Boolean hasFollowing = followingService.hasFollowing(following);
        return ResultUtils.success(hasFollowing);
    }

    @PostMapping("/getFollowingGroup")
    public BaseResponse getFollowingGroup(){
          return ResultUtils.success(followingService.getFollowingGroup());
    }

    @GetMapping("/getFollowing/{groupId}")
    public BaseResponse getFollowing(@PathVariable("groupId") Long groupId){
        List<UserVO> followingByGroupId = followingService.getFollowingByGroupId(groupId);
        return ResultUtils.success(followingByGroupId);
    }

    @GetMapping("/getFans")
    public BaseResponse getFans(){
        return ResultUtils.success(followingService.getFans());
    }

}
