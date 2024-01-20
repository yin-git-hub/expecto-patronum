package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.ScrollingMapper;
import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.entity.Scrolling;
import com.example.dao.model.vo.PageResult;
import com.example.service.SearchAllService;
import com.example.service.SearchService;
import com.example.service.common.ErrorCode;
import com.example.service.common.SearchType;
import com.example.service.exception.ThrowUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/21 12:22
 * Describe:
 */
@Service
public class SearchAllServiceImpl implements SearchAllService {

    HashMap<String, SearchService> searchServiceMap = new HashMap<String, SearchService>();
    @Autowired
    UserInfoSearchServiceImpl userInfoSearchService;
    @Autowired
    VideoInfoSearchServiceImpl videoInfoSearchService;
    @Autowired
    ScrollingSearchServiceImpl scrollingSearchService;
    @Autowired
    ScrollingMapper scrollingMapper;
    @Autowired
    UserSupport userSupport;

    @PostConstruct
    public void initMap(){
        searchServiceMap.put(SearchType.UserInfo.getType(),userInfoSearchService );
        searchServiceMap.put(SearchType.VideoInfo.getType(), videoInfoSearchService);
        searchServiceMap.put(SearchType.Scrolling.getType(),scrollingSearchService);
    }

    @Override
    public PageResult searchAllES(SearchDto searchDto) {

        ThrowUtils.throwIf(searchDto==null, ErrorCode.PARAMS_ERROR);
        String type = searchDto.getType();
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(!SearchType.VerifyType(type), ErrorCode.PARAMS_ERROR);

        SearchService o =   searchServiceMap.get(type);
        PageResult search = o.search(searchDto);

        return search;
    }

    @Override
    public PageResult searchAllESSelf(SearchDto searchDto) {
        ThrowUtils.throwIf(searchDto==null, ErrorCode.PARAMS_ERROR);
        String type = searchDto.getType();
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(!SearchType.VerifyType(type), ErrorCode.PARAMS_ERROR);
        Long userId = userSupport.getCurrentUserId();
        searchDto.setUserId(userId);
        SearchService o =   searchServiceMap.get(type);

        PageResult search = o.searchSelf(searchDto);

        return search;
    }

    @Override
    public PageResult searchAllMySQL(SearchDto searchDto) {
        List<Scrolling> search = scrollingMapper.selectAllSQL(searchDto);
        PageResult<Scrolling> objectPageResult = new PageResult<>();
        objectPageResult.setValList(search);


        return objectPageResult;
    }


}
