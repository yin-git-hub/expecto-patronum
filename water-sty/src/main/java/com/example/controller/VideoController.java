package com.example.controller;

 import com.example.dao.model.entity.Scrolling;
 import com.example.dao.model.entity.VideoInfo;
 import com.example.dao.model.entity.VideoRecord;
 import com.example.dao.model.vo.PageResult;
 import com.example.service.MinioService;
 import com.example.service.VideoService;
 import com.example.service.common.BaseResponse;
  import com.example.service.common.ResultUtils;
 import io.swagger.models.auth.In;
 import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 import javax.websocket.server.PathParam;
 import java.util.List;
 import java.util.Map;


@RestController
@Slf4j
@RequestMapping(value = "/video")
public class VideoController {

    @Autowired
    private MinioService minioService;
    @Autowired
    VideoService videoService;

    /**
     * 支持分段读取视频流
     *
     * @param request    请求对象
     * @param response   响应对象
     * @param bucketName 视频所在桶的位置
     * @param objectName 视频的文件名
     *
     * http://localhost:7330/water-sty/video/play/minio-demo/709d1d31dc47636e4f5ccbfd07601c19.mp4
     * http://localhost:7330/water-sty/video/home/minio-demo/709d1d31dc47636e4f5ccbfd07601c19.mp4
     *
     * http://localhost:7330/water-sty/video/home/minio-demo/709d1d31dc47636e4f5ccbfd07601c19.mp4
    */
    @GetMapping(value = "/play/{bucketName}/{objectName}")
    public void videoPlay(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable(value = "bucketName") String bucketName,
                                  @PathVariable(value = "objectName") String objectName) {
        minioService.videoPlay(request, response, bucketName, objectName);

    }

    @GetMapping(value = "/home/{bucketName}/{objectName}")
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
    @PostMapping("/getVideoUrl")
    public BaseResponse getVideoUrl(@RequestBody Map<String, Integer> requestBody){

        String url = videoService.getVideoUrl(requestBody.get("id"));
        return ResultUtils.success(url);
    }

    /**
     * 根据videoId获取弹幕
     * @param requestBody
     * @return
     */
    @PostMapping("/getScrolling")
    public BaseResponse getScrolling(@RequestBody Map<String, Integer> requestBody){

        List<Scrolling> list = videoService.getScrolling(requestBody.get("id"));
        return ResultUtils.success(list);
    }

    @PostMapping("/delVideo/{videoId}")
    public BaseResponse delVideo(@PathVariable("videoId") Long videoId){
        videoService.delVideo(videoId);
        return ResultUtils.success( );
    }

    @PostMapping("/addVideoRecord")
    public BaseResponse addVideoRecord(@RequestBody VideoRecord videoRecord){
        videoService.addVideoRecord(videoRecord);
        return ResultUtils.success();
    }

    @PostMapping("/getVideoRecord")
    public BaseResponse getVideoRecord(){
        List<VideoInfo> videoRecord = videoService.getVideoRecord();
        return ResultUtils.success(videoRecord);
    }

    /**
     * 根据审核状态和视频状态查询
     * @return
     */
    @PostMapping("/getVideoInfo/{ortherSort}/{id}")
    public BaseResponse getVideoInfo(
            @PathVariable("ortherSort") String ortherSort
            ,@PathVariable("id") Long id
    ){
        List<VideoInfo> videoInfos = videoService.getVideoInfo(ortherSort,id);
        return ResultUtils.success(videoInfos);
    }

}
