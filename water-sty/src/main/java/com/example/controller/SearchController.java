package com.example.controller;


import com.example.dao.model.dto.SearchDto;
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
        PageResult pageResult = searchService.searchAll(searchDto);
        return ResultUtils.success(pageResult);
    }
}
