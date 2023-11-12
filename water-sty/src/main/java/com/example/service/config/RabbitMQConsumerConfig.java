package com.example.service.config;

import com.example.dao.model.entity.Scrolling;
import com.example.service.common.ErrorCode;
import com.example.service.exception.ThrowUtils;
import com.example.service.impl.websocketImpl.ScrollingWebsocketServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Author: yin7331
 * Date: 2023/11/4 22:06
 * Describe:
 */
@Component
@Slf4j
public class RabbitMQConsumerConfig {
    @Autowired
    ScrollingWebsocketServiceImpl scrollingWebsocketService;


    @RabbitHandler
    @RabbitListener(queues = RabbitMQProducerConfig.SCROLLING_QUEUE)// 监听的队列名称 TestDirectQueue
    public void process(Scrolling message) {
        ThrowUtils.throwIf(scrollingWebsocketService==null, ErrorCode.OPERATION_ERROR,"error===scrollingWebsocketService");
        scrollingWebsocketService.sendMessageCurrentVideo(message);
        log.info("rabbitmq-consumer:{}", String.valueOf(message));


    }
}
