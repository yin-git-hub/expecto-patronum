package com.example.dao.model.entity;

import com.example.dao.model.vo.PageResult;
import com.example.service.constant.VideoConstant;
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
public class VideoInfo  extends PageResult implements Serializable {

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

    /**
     * 0-正常 1-异常
     */
    private Integer videoStatus= VideoConstant.STATUS_NORMAL;

    /**
     * 0-未审核 1-已审核 2-未通过
     */
    private Integer videoReview=VideoConstant.REVIEW_NO;

    /**
     *  0-正常 1-已删
     * */
    private Integer isDelete = VideoConstant.DELETE_NO;

    private Long worksLabelId;

}
