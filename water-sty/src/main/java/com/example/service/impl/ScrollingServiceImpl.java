package com.example.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.controller.ScrollingWebsocketController;
import com.example.dao.mapper.ScrollingMapper;
import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author: yin7331
 * Date: 2023/11/3 5:55
 * Describe:
 */
@Service
public class ScrollingServiceImpl implements ScrollingService {

    @Autowired
    StringRedisTemplate redisTemplate;

    private String SCROLLING_KEY = "ScrollingKey";

    @Autowired
    ScrollingMapper scrollingMapper;


    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Override
    public void saveScroller(Scrolling scrolling) {
        String videoid = String.valueOf(scrolling.getVideoId());
        redisTemplate.opsForList().rightPush(SCROLLING_KEY+":"+videoid, JSONUtil.toJsonStr(scrolling));
    }

    @Override
    public void testSaveScroller(Scrolling scrolling) {
        scrollingMapper.saveScrolling(scrolling);
    }


    @PostConstruct
    public void saveScrollingToDB() {

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        Integer i = 0 * 1000;
        pool.scheduleAtFixedRate(() -> {
            BoundHashOperations<String, Object, Object> r = redisTemplate.boundHashOps(SCROLLING_KEY);
            Set<Object> keys = r.keys();
            if (keys != null && !keys.isEmpty()) {
                for (Object key : keys) {
                    Object o = r.get(key);
                    List<Scrolling> list = JSONUtil.toList((String) o, Scrolling.class);
                    for (Scrolling scrolling : list) {
                        scrollingMapper.saveScrolling(scrolling);
                    }
                    r.delete(key);
                }
            }
        }, i, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
    }
}
