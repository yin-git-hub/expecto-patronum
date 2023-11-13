package com.example.dao.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
* 分片上传-分片任务记录
* @TableName video
*/
@Data
public class Video implements Serializable {

    /**
    *
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 所属桶名
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("所属桶名")
     private String bucketName;
    /**
    * 文件的key
    */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("文件的key")
     private String objectKey;
    /**
    *
    */
    @ApiModelProperty("")
    private Date createDate;
    /**
    *
    */
    @ApiModelProperty("")
    private Date updateDate;

}
