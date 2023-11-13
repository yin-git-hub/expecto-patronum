package com.example.controller;

 import com.example.dao.model.vo.PageResult;
 import com.example.service.MinioService;
 import com.example.service.common.BaseResponse;
  import com.example.service.common.ResultUtils;
 import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@Slf4j
@RequestMapping(value = "/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private MinioService minioService;


    /**
     * 支持分段读取视频流
     *
     * @param request    请求对象
     * @param response   响应对象
     * @param bucketName 视频所在桶的位置
     * @param objectName 视频的文件名
     *                   http://192.168.100.128:9000/minio-demo/abec9d44-a530-490f-bed2-d0bb329f09ea.mp4?Content-Disposition=attachment%3B%20filename%3D%22abec9d44-a530-490f-bed2-d0bb329f09ea.mp4%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20231113%2F%2Fs3%2Faws4_request&X-Amz-Date=20231113T135605Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=abba73384a16b4c4b922aaac69e368b7518ee91fb7d2afec925dbecc4e292eb5
     *                   http://127.0.0.1:7331/video/play/minio-demo/abec9d44-a530-490f-bed2-d0bb329f09ea.mp4.jpg?Content-Disposition=attachment%3B%20filename%3D%227ed3d9f9-c440-4b47-9faa-a66098e94052.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20231031%2F%2Fs3%2Faws4_request&X-Amz-Date=20231031T170133Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=6adc0f7388884bce80dde93c5d17ef2e50c50715017eeb708302305fef56f7fb
     *                   http://127.0.0.1:7331/video/play/minio-demo/460e2dbe-e4c2-4232-8a5b-60e054f35f0c.mp4?Content-Disposition=attachment%3B%20filename%3D%22460e2dbe-e4c2-4232-8a5b-60e054f35f0c.mp4%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20231031%2F%2Fs3%2Faws4_request&X-Amz-Date=20231031T162214Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=17a4539b7e14ab4fbba090d859e57f526ceef5f9fa285ebfdc90e7086a6533ab
     */
    @GetMapping(value = "/play/{bucketName}/{objectName}")
    public void videoPlay(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable(value = "bucketName") String bucketName,
                                  @PathVariable(value = "objectName") String objectName) {
        minioService.videoPlay(request, response, bucketName, objectName);

    }

    @RequestMapping(value = "/home/{bucketName}/{objectName}")
    public ModelAndView videoHome( @PathVariable(value = "bucketName") String bucketName,
                                   @PathVariable(value = "objectName") String objectName) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("bucketName", bucketName);
        modelAndView.addObject("objectName", objectName);
        modelAndView.setViewName("video");
        return modelAndView;
    }


    @GetMapping("/getVideoList/{pageIndex}/{pageSize}/{area}")
    public BaseResponse getVideoList(
            @PathVariable("pageIndex") Integer pageIndex,
            @PathVariable("pageSize") Integer pageSize,
            @PathVariable("area") Integer area
    ){
        PageResult pageResult = minioService.getVideoInfo(pageIndex,pageSize,area);
        return ResultUtils.success(pageResult);
    }
}
