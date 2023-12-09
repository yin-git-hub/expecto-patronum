package com.example.controller;


import com.example.service.MinioService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping(value = "/video")
@Slf4j
public class MinioFileController {
   @Autowired
   private MinioService minioService;


    @GetMapping(value = "/home")
    public ModelAndView homeUpload() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upload");
        return modelAndView;
    }

    /**
     * 根据文件大小和文件的md5校验文件是否存在
     * 暂时使用Redis实现，后续需要存入数据库
     * 实现秒传接口
     *
     * @param md5 文件的md5
     * @return 操作是否成功
     */
    @GetMapping(value = "/check")
    public BaseResponse checkFileExists(String md5) {
       minioService.uploadCheck(md5);
       return ResultUtils.success();
    }


    /**
     * 文件上传，适合大文件，集成了分片上传
     */
    @PostMapping(value = "/upload")
    public BaseResponse upload(HttpServletRequest req) throws ServletException, IOException {
        Integer upload = minioService.upload(req);
        return ResultUtils.success(upload,null);
    }
}

