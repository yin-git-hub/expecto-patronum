package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.example.dao.model.entity.Scrolling;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: yin7331
 * Date: 2023/11/26 22:22
 * Describe:
 */
@Slf4j
class ScrollingServiceImplTest {
    public static void main(String[] args) {
        Map<String, String> map1 = new HashMap<String, String>(){{
            put("1", "a");
            put("2", "b");
            put("3", "c");
        }};
        Map<String, String> map2 = new HashMap<String, String>(){{
            put("test1", "张三");
            put("test2", "李四");
            put("test3", "王五");
        }};
        Map<String, String> resultMap = new HashMap<String, String>(){{
            putAll(map1);
            putAll(map2);
        }};
        System.out.println(resultMap);
    } 
}
