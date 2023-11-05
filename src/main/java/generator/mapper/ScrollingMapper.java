package generator.mapper;

import generator.domain.Scrolling;

/**
* @author yin82
* @description 针对表【scrolling(弹幕)】的数据库操作Mapper
* @createDate 2023-11-03 06:11:35
* @Entity generator.domain.Scrolling
*/
public interface ScrollingMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Scrolling record);

    int insertSelective(Scrolling record);

    Scrolling selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Scrolling record);

    int updateByPrimaryKey(Scrolling record);

}
