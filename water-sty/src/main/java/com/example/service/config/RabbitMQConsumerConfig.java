package com.example.service.config;

import cn.hutool.json.JSONUtil;
import com.example.dao.model.entity.Scrolling;
import com.example.controller.ScrollingWebsocketController;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Author: yin7331
 * Date: 2023/11/4 22:06
 * Describe:
 */
@Component
@Slf4j
public class RabbitMQConsumerConfig {
    @Autowired
    ScrollingWebsocketController scrollingWebsocketService;


    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.SCROLLING_QUEUE)// 监听的队列名称 TestDirectQueue
    public void process(Message message,Channel channel) throws IOException {
        try{
            String msg = new String(message.getBody(), "UTF-8");
            Scrolling scrolling = JSONUtil.toBean(msg, Scrolling.class);
            scrollingWebsocketService.sendMessageCurrentVideo(scrolling);
        }catch (Exception e){
            try {
                channel.basicNack(
                        message.getMessageProperties().getDeliveryTag(),
                        false,
                        false);
            }catch (Exception ee){}
        }
    }
    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_SCROLLING_QUEUE )
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "UTF-8");
        Scrolling scrolling = JSONUtil.toBean(msg, Scrolling.class);
        scrollingWebsocketService.sendMessageCurrentVideo(scrolling);
        channel.basicAck(
                message.getMessageProperties().getDeliveryTag(),
                false );
    }
}
