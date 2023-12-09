package com.example.dao.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
*
* @TableName video_info
*/
@Data
public class VideoInfo implements Serializable {

    /**
    *
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    *
    */
    @ApiModelProperty("")
    private Long userId;
    /**
    *
    */
    @ApiModelProperty("")
    private Long videoId;
    /**
    *
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
     private String videoName;

    @ApiModelProperty("")
    private String videoCover;
    /**
    *
    */
    @Size(max= 4069,message="编码长度不能超过4069")
    @ApiModelProperty("")
     private String videoSummary;
    /**
    * 0-知识区；1-娱乐：2-音乐 ...
    */
    @ApiModelProperty("0-知识区；1-娱乐：2-音乐 ...")
    private Integer area;
    /**
    *
    */
    @ApiModelProperty("")
    private String videoSize;
    /**
    *
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
     private String videoMd5;
    /**
    *
    */
    @ApiModelProperty("")
    private Date cereteDate;
    /**
    *
    */
    @ApiModelProperty("")
    private Date updateDate;

}
