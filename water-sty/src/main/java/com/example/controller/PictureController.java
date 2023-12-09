package com.example.controller;

import com.example.service.MinioService;
import com.example.service.PictureService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: yin7331
 * Date: 2023/12/9 9:12
 * Describe:
 */
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    PictureService pictureService;

    @PostMapping("/upload")
    public BaseResponse pictureUpload(HttpServletRequest req){
        pictureService.saveVideoPicture( req);
        return ResultUtils.success();
    }
}
