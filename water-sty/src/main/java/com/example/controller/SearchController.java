package com.example.controller;


import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.entity.VideoInfo;
import com.example.dao.model.vo.PageResult;
import com.example.service.SearchAllService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: yin7331
 * Date: 2023/11/21 12:12
 * Describe:
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchAllService searchService;
    @PostMapping("/all")
    public BaseResponse search(@RequestBody SearchDto searchDto){
        PageResult pageResult = searchService.searchAllES(searchDto);
        return ResultUtils.success(pageResult);
    }
    @PostMapping("/all/self")
    public BaseResponse searchSelf(@RequestBody SearchDto searchDto){
        PageResult pageResult = searchService.searchAllESSelf(searchDto);
        return ResultUtils.success(pageResult);
    }

    @PostMapping("/all/collection")
    public BaseResponse searchCollection(@RequestBody SearchDto searchDto){
        PageResult pageResult = searchService.searchCollection(searchDto);
        return ResultUtils.success(pageResult);
    }
    @PostMapping("/all/collection/group")
    public BaseResponse searchCollectionByGroup(@RequestBody SearchDto searchDto){
        PageResult pageResult = searchService.searchCollectionByGroup(searchDto);
        return ResultUtils.success(pageResult);
    }

    @PostMapping("/all-sql")
    public BaseResponse searchsql(@RequestBody SearchDto searchDto){
        PageResult pageResult = searchService.searchAllMySQL(searchDto);
        return ResultUtils.success(pageResult);
    }

    @PostMapping("/getVideoInfo")
    public BaseResponse getVideoInfo(@RequestBody SearchDto searchDto){
        PageResult pageResult = searchService.getVideoInfo(searchDto);
        return ResultUtils.success(pageResult);
    }
}
