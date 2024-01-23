package com.example.controller;

import com.example.dao.model.entity.Collection;
import com.example.dao.model.entity.CollectionGroup;
import com.example.dao.model.vo.UserVO;
import com.example.service.CollectionService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
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
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    CollectionService collectionService;


    /**
     * 添加收藏分组
     * @param CollectionGroup
     * @return
     */
    @PostMapping("/addCollectionGroup")
    public BaseResponse addCollectionGroup(@RequestBody CollectionGroup CollectionGroup){
        collectionService.addCollectionGroup(CollectionGroup);
        return ResultUtils.success();
    }

    /**
     * 将收藏放到分组里
     * @param Collection
     * @return
     */
    @PostMapping("/addCollectionToGroup")
    public BaseResponse addCollectionToGroup(@RequestBody Collection Collection){
        collectionService.addCollectionToGroup(Collection);
        return ResultUtils.success();

    }

    /**
     * 添加收藏
     * @param Collection
     * @return
     */
    @PostMapping("/addCollection")
    public BaseResponse addCollection(@RequestBody Collection Collection){
        collectionService.saveCollection(Collection);
        return ResultUtils.success();
    }

    /**
     * 取消收藏
     * @param videoId
     * @return
     */
    @PostMapping("/deleteCollection/{videoId}")
    public BaseResponse deleteCollection(@PathVariable("videoId")String videoId){
        collectionService.deleteCollection(videoId);
        return ResultUtils.success();
    }

    /**
     * 是否存在该收藏
     * @param Collection
     * @return
     */

    @ApiOperation("hasCollection 是否存在该收藏")
    @PostMapping("/hasCollection")
    public BaseResponse hasCollection(@RequestBody Collection Collection){
        Boolean hasCollection = collectionService.hasCollection(Collection);
        return ResultUtils.success(hasCollection);
    }

    /**
     * 获取收藏分组
     * @return
     */
    @PostMapping("/getCollectionGroup")
    public BaseResponse getCollectionGroup(){
          return ResultUtils.success(collectionService.getCollectionGroup());
    }

    @PostMapping("/deleteCollectionGroup/{id}")
    public BaseResponse deleteCollectionGroup(@PathVariable("id")String id){
        collectionService.deleteCollectionGroup(id);
        return ResultUtils.success();
    }

    /**
     * 根据收藏分组获取收藏
     * @param groupId
     * @return
     */
    @GetMapping("/getCollection/{groupId}")
    public BaseResponse getCollection(@PathVariable("groupId") Long groupId){
        List<UserVO> CollectionByGroupId = collectionService.getCollectionByGroupId(groupId);
        return ResultUtils.success(CollectionByGroupId);
    }

    /**
     * 获取添加过收藏的分组
     * @param Collection
     * @return
     */
    @PostMapping("/getChoosedGroups")
    public BaseResponse getChoosedGroups(@RequestBody Collection Collection){
        List choosedGroups = collectionService.getChoosedGroups(Collection);
        return ResultUtils.success(choosedGroups);
    }

}
