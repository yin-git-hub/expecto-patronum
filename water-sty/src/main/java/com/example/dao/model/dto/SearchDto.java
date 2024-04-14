package com.example.dao.model.dto;

import com.example.dao.model.vo.PageResult;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/21 12:14
 * Describe:
 */
@Data
public class SearchDto extends PageResult implements Serializable{

    private String type;

    private String content;

    private Long userId;

    private Long worksLabelId;

    private List<Long> videoIds;

    // 收藏分组ID
    private Long collectionGroupId;

}
