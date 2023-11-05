package com.example.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: yin7331
 * Date: 2023/11/4 21:54
 * Describe:
 */
@Configuration
public class RabbitMQProducerConfig {
    // 绑定键
    public final static String SCROLLING_QUEUE = "scrolling-queue";
    public final static String SCROLLING_EXCHANGE = "scrolling-exchange";


    @Bean
    public Queue scrollingQueue() {
        return new Queue(SCROLLING_QUEUE,true);
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

}
