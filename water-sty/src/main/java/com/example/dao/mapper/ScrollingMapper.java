package com.example.dao.mapper;


import com.example.dao.model.entity.Scrolling;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: yin7331
 * Date: 2023/11/3 12:11
 * Describe:
 */
@Mapper
public interface ScrollingMapper {

    Integer saveScrolling(Scrolling scrolling);
}
