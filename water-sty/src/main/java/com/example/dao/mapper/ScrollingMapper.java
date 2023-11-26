package com.example.dao.mapper;


import com.example.dao.model.dto.SearchDto;
import com.example.dao.model.entity.Scrolling;
import com.example.dao.model.vo.PageResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/3 12:11
 * Describe:
 */
@Mapper
public interface ScrollingMapper {

    Integer saveScrolling(Scrolling scrolling);

    List<Scrolling> selectAllSQL(SearchDto searchDto);
}
