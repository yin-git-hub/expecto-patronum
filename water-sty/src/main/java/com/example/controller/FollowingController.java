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


    /**
     * 添加关注分组
     * @param followingGroup
     * @return
     */
    @PostMapping("/addFollowingGroup")
    public BaseResponse addFollowingGroup(@RequestBody FollowingGroup followingGroup){
        followingService.addFollowingGroup(followingGroup);
        return ResultUtils.success();
    }

    /**
     * 将关注放到分组里
     * @param following
     * @return
     */
    @PostMapping("/addFollowingToGroup")
    public BaseResponse addFollowingToGroup(@RequestBody Following following){
        followingService.addFollowingToGroup(following);
        return ResultUtils.success();

    }

    /**
     * 添加关注
     * @param following
     * @return
     */
    @PostMapping("/addFollowing")
    public BaseResponse addFollowing(@RequestBody Following following){
        followingService.saveFollowing(following);
        return ResultUtils.success();
    }

    /**
     * 取消关注
     * @param upId
     * @return
     */
    @PostMapping("/deleteFollowing/{upId}")
    public BaseResponse deleteFollowing(@PathVariable("upId")String upId){
        followingService.deleteFollowing(upId);
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

    /**
     * 获取关注分组
     * @return
     */
    @PostMapping("/getFollowingGroup")
    public BaseResponse getFollowingGroup(){
          return ResultUtils.success(followingService.getFollowingGroup());
    }

    /**
     * 根据关注分组获取关注
     * @param groupId
     * @return
     */
    @GetMapping("/getFollowing/{groupId}")
    public BaseResponse getFollowing(@PathVariable("groupId") Long groupId){
        List<UserVO> followingByGroupId = followingService.getFollowingByGroupId(groupId);
        return ResultUtils.success(followingByGroupId);
    }

    /**
     * 获取添加过关注的分组
     * @param following
     * @return
     */
    @PostMapping("/getChoosedGroups")
    public BaseResponse getChoosedGroups(@RequestBody Following following){
        List choosedGroups = followingService.getChoosedGroups(following);
        return ResultUtils.success(choosedGroups);
    }

    /**
     * 获取获取粉丝
     * @return
     */
    @GetMapping("/getFans")
    public BaseResponse getFans(){
        return ResultUtils.success(followingService.getFans());
    }

}
