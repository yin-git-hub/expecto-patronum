package com.example.dao.mapper;

import com.example.dao.model.entity.*;
import com.example.dao.model.vo.VideoReportMsgVO;
import com.example.dao.model.vo.VideoUploadMsgVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: yin7331
 * Date: 2023/12/9 19:33
 * Describe:
 */
@Mapper
public interface VideoMapper {
    void savePicturePath(@Param("picturePath") String picturePath,@Param("md5") String md5);

    void saveVideo(Video video);
    Map<String, String> getVideoUrlFromVideo(@Param("id") Integer id);

    List<Scrolling> getScrolling(@Param("id") Integer id);

    List<WorksLabel> getVideoLabel();

    void delVideo(Long videoId);

    List<VideoInfo> getVideoInfo(@Param("worksLabelId") Integer worksLabelId,@Param("offset")Integer offset,@Param("pageSize")Integer pageSize);

    List<VideoInfo> getVideoInfoCount(Long worksLabelId);

    void addVideoRecordToDB(VideoRecord videoRecord);

    List<VideoRecord> getVideoRecord(VideoRecord videoRecord);

    void updateVideoRecordToDB(VideoRecord videoRecord);

    List<VideoRecord> getVideoRecordByUserId(Long currentUserId);

     VideoInfo getVideoInfoByVideoId(Long videoId);

    List<VideoInfo> getVideoInfoByUserIdAndVideoStatus(@Param("currentUserId") Long currentUserId, @Param("id")Long id);

    List<VideoInfo> getVideoInfoByUserIdAndVideoReview(@Param("currentUserId") Long currentUserId, @Param("id")Long id);

    List<VideoInfo> getVideoInfoByUserId(Long userId);

    List<VideoReportMsgVO> getVideoReportMsg(Long currentUserId);

    List<VideoUploadMsgVO> getVideoUploadMsg(Long currentUserId);

}
