package com.example.service.impl;

import com.example.dao.mapper.WorksLabelMapper;
import com.example.dao.model.entity.WorksLabel;
import com.example.service.WorkLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2024/3/30 20:11
 * Describe:
 */
@Service
public class WorkLabelServiceImpl implements WorkLabelService {

    @Autowired
    WorksLabelMapper worksLabelMapper;
    @Override
    public List<WorksLabel> getVideoLabel() {
        List<WorksLabel> videoLabel = worksLabelMapper.getVideoLabel();
        return videoLabel;
    }
}
