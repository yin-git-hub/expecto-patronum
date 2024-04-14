package com.example.service;

import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.vo.PageResult;

/**
 * Author: yin7331
 * Date: 2023/11/21 12:22
 * Describe:
 */
public interface SearchAllService {
    PageResult searchAllES(SearchDto searchDto);
    PageResult searchAllMySQL(SearchDto searchDto);

    PageResult searchAllESSelf(SearchDto searchDto);

    PageResult searchCollection(SearchDto searchDto);

    PageResult searchCollectionByGroup(SearchDto searchDto);


    PageResult getVideoInfo(SearchDto searchDto);
}
