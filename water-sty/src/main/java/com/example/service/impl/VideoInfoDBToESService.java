package com.example.service.impl;


import com.alibaba.otter.canal.protocol.CanalEntry;
import com.example.dao.model.entity.VideoInfoES;
import com.example.service.DBToESService;
import com.example.service.VideoInfoESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/20 20:10
 * Describe:
 */
@Component
public class VideoInfoDBToESService  implements DBToESService {

    @Autowired
    VideoInfoESService videoInfoESService;
    @Override
    public void printColumn(List<CanalEntry.Column> columns) {
        VideoInfoES videoInfoES = new VideoInfoES();
        for (CanalEntry.Column column : columns) {
            if (column.getName().equals("id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setId(Long.valueOf(value));

            } else if (column.getName().equals("user_id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setUserId(Long.valueOf(value));

            }else if (column.getName().equals("video_id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setVideoId(Long.valueOf(value));
            } else if (column.getName().equals("video_name")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setVideoName(value);
            }else if (column.getName().equals("video_cover")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setVideoCover(value);
            }else if (column.getName().equals("video_summary")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setVideoSummary(value);
            }
            /**
             * videoStatus
             * videoReview
             * isDelete
             * worksLabelId
             */
            else if (column.getName().equals("video_status")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setVideoStatus(value);
            }else if (column.getName().equals("video_review")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setVideoReview(value);
            }else if (column.getName().equals("is_delete")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setIsDelete(value);
            }else if (column.getName().equals("works_label_id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                videoInfoES.setWorksLabelId(value);
            }
        }
        videoInfoESService.save(videoInfoES);
    }

    @Override
    public void deleteColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            if (column.getName().equals("video_id")) {
                String value = column.getValue();
                videoInfoESService.deleteByVideoId(value);
            }
        }
    }


}
