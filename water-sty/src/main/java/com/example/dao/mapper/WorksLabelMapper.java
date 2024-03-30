package com.example.dao.mapper;

import com.example.dao.model.entity.WorksLabel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2024/3/30 20:12
 * Describe:
 */
@Mapper
public interface WorksLabelMapper {

    List<WorksLabel> getVideoLabel();
}
