package com.example.service.impl;

import com.example.service.MinioService;
import com.example.service.PictureService;
import com.example.service.common.ErrorCode;
import com.example.service.common.ResultUtils;
import com.example.service.exception.ThrowUtils;
import com.yin.minio.springminiostart.MinioTemplate;
import com.yin.minio.springminiostart.entity.OssFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
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

    String VIDEO_PICTURE_BUCKET = "video_picture_bucket";

    @Override
    public void saveVideoPicture(HttpServletRequest req) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

        // 获得文件分片数据
        // ((MultipartHttpServletRequest) req).getFile()
        MultipartFile file = multipartRequest.getFile("picture");

        // 上传过程中出现异常，状态码设置为50000
        // ThrowUtils.throwIf(file == null, ErrorCode.OPERATION_ERROR);
        String md5 = multipartRequest.getParameter("md5");

        // 创建文件桶
        boolean b = minioTemplate.bucketExists(VIDEO_PICTURE_BUCKET);
        System.out.println(b);
        // minioTemplate.makeBucket(md5);
        // String objectName = String.valueOf(index);
        //
        //
        // // 当不是最后一片时，上传返回的状态码为20001
        //
        // try {
        //     // 上传文件
        //     OssFile ossFile = minioTemplate.putChunkObject(file.getInputStream(), md5, objectName);
        //
        //
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}
