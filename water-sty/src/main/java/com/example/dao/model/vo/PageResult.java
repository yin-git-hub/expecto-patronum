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
    // 当前页码
    Integer pageIndex = 1;
    // 请求数据量
    Integer pageSize = 10;
    // 共有多少页
    Integer totalPages = 0;
    // 共有多少数据量
    Long totalElements;
    //单页数据量
    Integer total;
    List<T> valList;
}
