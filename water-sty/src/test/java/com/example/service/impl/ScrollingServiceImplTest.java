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

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if(j==3){
                    break;
                }
                System.out.println("j"+j);
            }
            System.out.println("i"+i);
        }


    }

}
