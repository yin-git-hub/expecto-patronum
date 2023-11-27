package com.example.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.dao.model.entity.Scrolling;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: yin7331
 * Date: 2023/11/26 22:22
 * Describe:
 */
class ScrollingServiceImplTest {
    public static void main(String[] args) {
        String i = "[\n" +
                "  {\n" +
                "    \"videoId\": 111,\n" +
                "    \"scrollingContext\": \"123\",\n" +
                "    \"relativeTime\": 1701007361775\n" +
                "  },\n" +
                "  {\n" +
                "    \"videoId\": 111,\n" +
                "    \"scrollingContext\": \"223343\",\n" +
                "    \"relativeTime\": 1701007865067\n" +
                "  }\n" +
                "]";
        List<Scrolling> list = JSONUtil.toList(i, Scrolling.class);
        System.out.println(list);
    }

}
