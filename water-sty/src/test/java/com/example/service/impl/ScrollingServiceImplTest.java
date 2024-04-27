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
        HashMap<String, Integer> map = new HashMap<>();

        // 向HashMap中添加键值对
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);

        // 获取并打印键为"key2"的值
        Integer value = map.get("key2");
        System.out.println("The value for 'key2' is: " + value); // 输出: The value for 'key2' is: 2

        // 尝试获取不存在的键，将返回null
        Integer nonExistingValue = map.get("key4");
        System.out.println("The value for 'key4' is: " + nonExistingValue); // 输出: The val
    } 
}
