package com.example.dao.model.vo;

import com.example.dao.model.dto.UserFollingDto;
import com.example.dao.model.entity.User;
import com.example.dao.model.entity.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/14 16:13
 * Describe:
 */
@Data
public class FollowingVO {
    private Long id;

    private Integer groupId=0;

    private String groupName;

    private List<UserFollingDto> userFollingDtoList;

}
