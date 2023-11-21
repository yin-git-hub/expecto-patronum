package com.example.service;

import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.vo.PageResult;

/**
 * Author: yin7331
 * Date: 2023/11/21 19:23
 * Describe:
 */
public interface SearchService {
    PageResult search(SearchDto searchDto);

}
