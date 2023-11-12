package com.example.service.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: yin7331
 * Date: 2023/11/4 21:54
 * Describe:
 */
@Configuration
public class RabbitMQConfig {
    // 绑定键
    public final static String SCROLLING_QUEUE = "scrolling_queue";
    public final static String SCROLLING_EXCHANGE = "scrolling_exchange";
    public final static String DEAD_LETTER_SCROLLING_QUEUE = "dead_letter_scrolling_queue";
    public final static String DEAD_LETTER_SCROLLING_EXCHANGE = "dead_letter_scrolling_exchange";
    public final static String DEAD_LETTER_SCROLLING_QUEUE_ROUTING = "dead_letter_scrolling_queue_routing";


    @Bean
    public Queue scrollingQueue() {// args.put("x-message-ttl", 10000);
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DEAD_LETTER_SCROLLING_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_SCROLLING_QUEUE_ROUTING);
         return new Queue(SCROLLING_QUEUE, true, false, false, args);
    }

    @Bean
    TopicExchange scrollingExchange() {
        return new TopicExchange(SCROLLING_EXCHANGE);
    }


    // 将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    // 这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder
                .bind(scrollingQueue())
                .to(scrollingExchange())
                .with(SCROLLING_EXCHANGE);
    }

    // 声明死信Exchange
    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DEAD_LETTER_SCROLLING_EXCHANGE);
    }
    @Bean
    public Queue deadLetterQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3000);
        return new Queue(DEAD_LETTER_SCROLLING_QUEUE,true,false,false,args);
    }

    // bing dead letter
    @Bean
    Binding bindingDeadLetterExchangeMessage() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_SCROLLING_QUEUE_ROUTING);
    }

}
