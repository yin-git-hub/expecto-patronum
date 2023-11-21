package com.example.dao.model.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * Author: yin7331
 * Date: 2023/11/18 20:37
 * Describe:
 */

@Data
@Document(indexName = "userinfoes")
public class UserInfoES implements Serializable {
    @Field(type = FieldType.Keyword)
    Long id;
    @Field(type = FieldType.Keyword)
    Long userId;
    @Field(type = FieldType.Text)
    String nickname;
    @Field(type = FieldType.Keyword)
    String signature;
    @Field(type = FieldType.Keyword)
    String image;

}
