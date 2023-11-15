package com.example.dao.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Author: yin7331
 * Date: 2023/11/15 5:44
 * Describe:
 */
@Data
public class PushMessageEntity {


    private Long id;

    private Long userId;

    private String nickname;

    private Long videoId;

    private String videoName;

    private String videoCover;

    private String videoSummary;

    private Integer area;

    private Long videoSize;

    private String videoMd5;

}
