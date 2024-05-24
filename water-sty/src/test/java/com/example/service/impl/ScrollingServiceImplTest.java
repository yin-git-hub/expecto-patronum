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
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            log.debug("1开始运行...");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("1运行结束...");
        }, "daemon");
        // 设置该线程为守护线程
//        t1.setDaemon(true);
        t1.start();
        Thread.sleep(1);
        log.debug("运行结束...");
     }
    private static void test1() throws Exception {
        Thread t1 = new Thread(()->{

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, "t1");
        t1.start();
        log.debug(" 打断状态: {}", t1.isInterrupted());
        Thread.sleep((long) 0.5);
        t1.interrupt();
        log.debug(" 打断状态: {}", t1.isInterrupted());
    }

}
