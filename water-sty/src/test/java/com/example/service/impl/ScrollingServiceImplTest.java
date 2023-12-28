package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.example.dao.model.entity.Scrolling;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: yin7331
 * Date: 2023/11/26 22:22
 * Describe:
 */
@Slf4j
class ScrollingServiceImplTest {
    public static void main(String[] args) {
        List<Long> groupIds = new ArrayList<>();
        List<Long> groupsByUserIdAndUpId = new ArrayList<>();

        // 添加一些元素到两个列表
        groupIds.add(1L);
        groupIds.add(2L);
        groupIds.add(3L);

        groupsByUserIdAndUpId.add(6L);
        groupsByUserIdAndUpId.add(4L);
        groupsByUserIdAndUpId.add(5L);

        // 创建一个临时列表来保存交集

        System.out.println(CollUtil.containsAny(groupIds,groupsByUserIdAndUpId));


    }

}
