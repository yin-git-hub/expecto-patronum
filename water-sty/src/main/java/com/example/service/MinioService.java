package com.example.service;

import com.example.dao.model.vo.PageResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author lyf
 * @version 1.0
 * @classname MinioService
 * @description 这里统一作为Minio文件业务处理类接口
 * @since 2023/4/12 15:37
 */
public interface MinioService {

    /**
     * 文件上传前的检查，这是为了实现秒传接口
     *
     * @param md5 文件的md5
     * @return 文件是否上传过的元数据
     */
    void uploadCheck(String md5);

    /**
     * 文件上传的核心功能
     *
     * @param req 请求
     * @return 上传结果的元数据
     */
    Integer upload(HttpServletRequest req) throws ServletException, IOException;

    /**
     * 分片文件合并的核心方法
     *
     * @param shardCount 分片数
     * @param md5        文件的md5值
     * @param fileSize   文件大小
     * @return 合并成功的元数据
     */


    /**
     * 视频播放的核心功能
     *
     * @param request    request
     * @param response   response
     * @param bucketName 视频文件所在的桶
     * @param objectName 视频文件名
     */
    void videoPlay(HttpServletRequest request, HttpServletResponse response,
                   String bucketName,
                   String objectName);

    PageResult getVideoInfo(Integer pageIndex, Integer pageSize, Integer area);
}
