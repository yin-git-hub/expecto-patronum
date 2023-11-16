package com.example.dao.model.entity;

/**
 * Author: yin7331
 * Date: 2023/11/16 6:03
 * Describe:
 */

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Date;

/**
 * 弹幕
 * @TableName scrolling
 */
@Data
@Document(indexName = "scrollinges")
public class ScrollingES implements Serializable {
    /**
     *
     */
    @Field(type = FieldType.Keyword)
    private Long id;

    /**
     *
     */
    @Field(type = FieldType.Keyword)
    private Long userId;

    /**
     *
     */
    @Field(type = FieldType.Keyword)
    private Long videoId;//reference
    /**
     * 弹幕内容
     */
    @Field(type = FieldType.Text)
    private String scrollingContext;
    /**
     * 弹幕出现的相对时间
     */
    @Field(type = FieldType.Long)
    private Long relativeTime;

    /**
     *
     */
    @Field(type = FieldType.Date)
    private Date createDate;

    /**
     *
     */
    @Field(type = FieldType.Date)
    private Date updateDate;
}
