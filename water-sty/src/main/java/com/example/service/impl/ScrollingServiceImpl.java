package com.example.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.dao.mapper.ScrollingMapper;
import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
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

    @Override
    public void saveScroller(Scrolling scrolling) {

        String videoid = String.valueOf(scrolling.getVideoId());
        redisTemplate.opsForList().rightPush(SCROLLING_KEY + ":" + videoid, JSONUtil.toJsonStr(scrolling));
    }

    @Override
    public void testSaveScroller(Scrolling scrolling) {
        scrollingMapper.saveScrolling(scrolling);
    }


    @PostConstruct
    public void saveScrollingToDB() {

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        Integer i = 3 * 1000;
        pool.scheduleAtFixedRate(() -> {
            Set<String> keys1 = redisTemplate.keys(SCROLLING_KEY + ":*");
            for (String redisKey : keys1) {
                System.out.println(redisKey);

                List<Scrolling> scrollingList = JSONUtil.toList(
                        redisTemplate.opsForList().leftPop(redisKey, 100).toString(),
                        Scrolling.class);
                System.out.println(scrollingList);
                scrollingList.stream().forEach(s->{
                    scrollingMapper.saveScrolling(s);
                });
            }
            // todo 测试后改为一天
        }, i, 1000 * 3, TimeUnit.MILLISECONDS);
    }
}
