package com.example.dao.model.dto;

import com.example.dao.model.vo.PageResult;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: yin7331
 * Date: 2023/11/21 12:14
 * Describe:
 */
@Data
public class SearchDto extends PageResult implements Serializable{

    private String type;

    private String content;

}
