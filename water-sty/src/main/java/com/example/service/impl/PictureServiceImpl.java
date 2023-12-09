package com.example.service.impl;

import com.example.service.MinioService;
import com.example.service.PictureService;
import com.example.service.VideoInfoESService;
import com.example.service.VideoService;
import com.example.service.common.ErrorCode;
import com.example.service.common.ResultUtils;
import com.example.service.exception.ThrowUtils;
import com.yin.minio.springminiostart.MinioTemplate;
import com.yin.minio.springminiostart.entity.OssFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Author: yin7331
 * Date: 2023/12/9 9:17
 * Describe:
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private MinioTemplate minioTemplate;
    @Autowired
    VideoService videoService;

    String VIDEO_PICTURE_BUCKET = "video-picture-bucket";

    @Override
    public void saveVideoPicture(HttpServletRequest req) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

        // 获得文件分片数据
        // ((MultipartHttpServletRequest) req).getFile()
        MultipartFile file = multipartRequest.getFile("picture");

        // 上传过程中出现异常，状态码设置为50000
        ThrowUtils.throwIf(file == null, ErrorCode.OPERATION_ERROR);
        String md5 = multipartRequest.getParameter("md5");
        String suffix = multipartRequest.getParameter("suffix");
        ThrowUtils.throwIf(StringUtils.isBlank(md5), ErrorCode.OPERATION_ERROR,"请先上传视频");
        String pictureName = "picture:"+md5+"."+suffix;

        // 创建文件桶
        if (!minioTemplate.bucketExists(VIDEO_PICTURE_BUCKET)) {
            minioTemplate.makeBucket(VIDEO_PICTURE_BUCKET);
        }
        try {
            // 上传文件
            minioTemplate.putChunkObject(file.getInputStream(), VIDEO_PICTURE_BUCKET, pictureName);
            String picturePath = "http://localhost:7330/water-sty/picture/"+VIDEO_PICTURE_BUCKET+"/"+pictureName;
            videoService.savePicturePath(picturePath,md5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getPictureUrl(String bucketName, String objectName) {
        InputStream imageStream = minioTemplate.getObject(bucketName, objectName);
        return imageStream;
        // minioTemplate.getObjectInfo(bucketName, objectName);picture%3A709d1d31dc47636e4f5ccbfd07601c19.png
    }
}
