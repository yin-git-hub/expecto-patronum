package com.example.service.impl;

import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.entity.ScrollingES;
import com.example.dao.model.vo.PageResult;
import com.example.service.ScrollingESService;
import com.example.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Author: yin7331
 * Date: 2023/11/21 15:23
 * Describe:
 */
@Service
public class ScrollingSearchServiceImpl implements SearchService {
    @Autowired
    ScrollingESService scrollingESService;


    @Override
    public PageResult search(SearchDto searchDto) {
        String content = searchDto.getContent();
        Pageable pageable = PageRequest.of(searchDto.getPageIndex()-1, searchDto.getPageSize());
        Page<ScrollingES> videoInfoES =  scrollingESService.findByScrollingContextLike(content,pageable);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(videoInfoES.getSize());
        pageResult.setValList(videoInfoES.getContent());
        return pageResult;
    }

    @Override
    public PageResult searchSelf(SearchDto searchDto) {
        return null;
    }

    @Override
    public PageResult searchCollection(SearchDto searchDto) {
        return null;
    }

}
