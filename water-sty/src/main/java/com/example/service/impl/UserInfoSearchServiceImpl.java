package com.example.service.impl;

import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.entity.UserInfoES;
import com.example.dao.model.vo.PageResult;
import com.example.service.SearchService;
import com.example.service.UserInfoESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Author: yin7331
 * Date: 2023/11/21 13:08
 * Describe:
 */
@Service
public class UserInfoSearchServiceImpl implements SearchService {

    @Autowired
    UserInfoESService userInfoESService;

    @Override
    public PageResult search(SearchDto searchDto) {
        String content = searchDto.getContent();
        Pageable pageable = PageRequest.of(searchDto.getPageIndex()-1, searchDto.getPageSize());
        Page<UserInfoES> users = userInfoESService.findByNicknameLike(content,pageable);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(users.getSize());
        pageResult.setValList(users.getContent());
        return pageResult ;
    }

}
