package com.example.dao.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/1 21:43
 * Describe:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    //总数
    Integer total;
    List<T> valList;
}
