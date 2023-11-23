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
    @PostMapping(value = "/upload-vue")
    public BaseResponse upload_vue(HttpServletRequest req) throws ServletException, IOException {
        Integer upload = minioService.upload_vue(req);
        return ResultUtils.success(upload,null);
    }

    /**
     * 文件合并
     *
     * @param shardCount 分片总数
     * @param fileName   文件名
     * @param md5        文件的md5
     * @param fileType   文件类型
     * @param fileSize   文件大小
     * @return 分片合并的状态
     */
    @GetMapping(value = "/merge")
    public BaseResponse merge(Integer shardCount, String fileName, String md5, String fileType,
                                     Long fileSize,Integer area) {

        minioService.merge(shardCount, fileName, md5, fileType, fileSize,area);
        return ResultUtils.success();
    }
}

