package com.example.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.dao.mapper.ScrollingMapper;
import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<Scrolling> scrollingList = JSONUtil.toList((String) redisTemplate.boundHashOps(SCROLLING_KEY).get(videoid), Scrolling.class);


        if(scrollingList==null){
            scrollingList = new LinkedList<>();
        }
        scrollingList.add(scrolling);
        String jsonStrScrolling = JSONUtil.toJsonStr(scrollingList);
        redisTemplate.boundHashOps(SCROLLING_KEY).put(videoid,jsonStrScrolling );

        // saveScrollingToDB(scrolling);

    }


    @Override
    @Async
    public void saveScrollingToDB(Scrolling scrolling){

        scrollingMapper.saveScrolling(scrolling);
    }
}
