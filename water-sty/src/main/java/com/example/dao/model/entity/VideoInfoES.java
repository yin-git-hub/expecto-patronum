package com.example.dao.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Author: yin7331
 * Date: 2023/11/20 20:50
 * Describe:
 */
@Data
@Document(indexName = "videoinfoes")
public class VideoInfoES {
    @Field(type = FieldType.Keyword)
    Long id;
    @Field(type = FieldType.Keyword)
    Long userId;
    @Field(type = FieldType.Keyword)
    Long videoId;
    @Field(type = FieldType.Text)
    String videoName;
    @Field(type = FieldType.Keyword)
    String videoSummary;
    @Field(type = FieldType.Keyword)
     private Long videoSize;
    @Field(type = FieldType.Keyword)
    private String videoCover;
    @Field(type = FieldType.Keyword)
    private Integer videoStatus;
    @Field(type = FieldType.Keyword)
    private Integer videoReview;
    @Field(type = FieldType.Keyword)
    private Integer isDelete;
    @Field(type = FieldType.Keyword)
    private Long worksLabelId;
}
