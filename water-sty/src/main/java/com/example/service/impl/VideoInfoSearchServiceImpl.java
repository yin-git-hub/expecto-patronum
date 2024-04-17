package com.example.service.impl;

import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.entity.VideoInfoES;
import com.example.dao.model.vo.PageResult;
import com.example.service.SearchService;
import com.example.service.VideoInfoESService;
import com.example.service.common.ErrorCode;
import com.example.service.constant.VideoConstant;
import com.example.service.exception.ThrowUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/21 15:23
 * Describe:
 */
@Service
public class VideoInfoSearchServiceImpl implements SearchService {
    @Autowired
    VideoInfoESService videoInfoESService;


    @Override
    public PageResult search(SearchDto searchDto) {
        String content = searchDto.getContent();
        Pageable pageable = PageRequest.of(searchDto.getPageIndex() - 1, searchDto.getPageSize());
        Page<VideoInfoES> videoInfoES;
        if (StringUtils.isBlank(content)) {
            videoInfoES = videoInfoESService.findAll(pageable);
        } else {
            videoInfoES = videoInfoESService.findByVideoNameLike(content, pageable);
        }
        PageResult pageResult = new PageResult();
        pageResult.setTotal(videoInfoES.getSize());
        LinkedList<VideoInfoES> videoInfoES1 = new LinkedList<>();
        List<VideoInfoES> content1 = videoInfoES.getContent();
        for (VideoInfoES infoES : content1) {
//            已审核 并且 状态正常
            if (infoES.getVideoReview().equals(VideoConstant.REVIEW_YES)&&infoES.getVideoStatus().equals(VideoConstant.STATUS_NORMAL)) {
                videoInfoES1.add(infoES);
            }
        }
        pageResult.setValList(videoInfoES1);
        pageResult.setTotalPages(videoInfoES.getTotalPages());
        pageResult.setPageIndex(videoInfoES.getNumber() + 1);
        pageResult.setTotalElements(videoInfoES.getTotalElements());
        return pageResult;
    }

    @Override
    public PageResult searchSelf(SearchDto searchDto) {
        Long userId = searchDto.getUserId();
        ThrowUtils.throwIf(userId == null, ErrorCode.OPERATION_ERROR);
        Pageable pageable = PageRequest.of(searchDto.getPageIndex() - 1, searchDto.getPageSize());
        Page<VideoInfoES> videoInfoES = videoInfoESService.findByUserId(userId, pageable);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(videoInfoES.getSize());
        pageResult.setValList(videoInfoES.getContent());
        pageResult.setTotalPages(videoInfoES.getTotalPages());
        pageResult.setPageIndex(videoInfoES.getNumber() + 1);
        pageResult.setTotalElements(videoInfoES.getTotalElements());
        return pageResult;
    }

    @Override
    public PageResult searchCollection(SearchDto searchDto) {
        Long userId = searchDto.getUserId();
        ThrowUtils.throwIf(userId == null, ErrorCode.OPERATION_ERROR);
        Pageable pageable = PageRequest.of(searchDto.getPageIndex() - 1, searchDto.getPageSize());
        Page<VideoInfoES> byVideoIdIn = videoInfoESService.findByVideoIdIn(searchDto.getVideoIds(), pageable);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(byVideoIdIn.getSize());
        pageResult.setValList(byVideoIdIn.getContent());
        pageResult.setTotalPages(byVideoIdIn.getTotalPages());
        pageResult.setPageIndex(byVideoIdIn.getNumber() + 1);
        pageResult.setTotalElements(byVideoIdIn.getTotalElements());
        return pageResult;
    }

    @Override
    public PageResult getVideoInfo(SearchDto searchDto) {
        Pageable pageable = PageRequest.of(searchDto.getPageIndex() - 1, searchDto.getPageSize());
        Page<VideoInfoES> byVideoIdIn = videoInfoESService.findByWorksLabelId(searchDto.getWorksLabelId(), pageable);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(byVideoIdIn.getSize());
        LinkedList<VideoInfoES> videoInfoES1 = new LinkedList<>();
        List<VideoInfoES> content1 = byVideoIdIn.getContent();
        for (VideoInfoES infoES : content1) {
//            已审核 并且 状态正常
            if (infoES.getVideoReview().equals(VideoConstant.REVIEW_YES)&&infoES.getVideoStatus().equals(VideoConstant.STATUS_NORMAL)) {
                videoInfoES1.add(infoES);
            }
        }
        pageResult.setValList(videoInfoES1);
        pageResult.setTotalPages(byVideoIdIn.getTotalPages());
        pageResult.setPageIndex(byVideoIdIn.getNumber() + 1);
        pageResult.setTotalElements(byVideoIdIn.getTotalElements());
        return pageResult;
    }
}
