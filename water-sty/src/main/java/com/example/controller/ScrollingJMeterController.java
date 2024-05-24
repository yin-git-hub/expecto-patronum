package com.example.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.model.entity.Scrolling;
import com.example.service.ScrollingService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;
import com.example.service.exception.ThrowUtils;
import com.example.service.utils.RabbitMQUtil;
import com.example.service.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Author: yin7331
 * Date: 2023/11/2 22:35
 * Describe: 弹幕 scrolling
 */
@RequestMapping("/jmeter")
@RestController
public class ScrollingJMeterController {

    @Autowired
    ScrollingService scrollingService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void onMessage(String message) {


        // ThrowUtils.throwIf(this.userId==null, ErrorCode.NOT_LOGIN_ERROR);
        Scrolling scrolling = JSONObject.parseObject(message, Scrolling.class);

        RabbitMQUtil.asyncSendMessage(scrolling, rabbitTemplate);

        scrollingService.saveScroller(scrolling);

    }
}


