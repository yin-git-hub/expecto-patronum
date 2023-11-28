package com.example.service.utils;

import cn.hutool.json.JSONUtil;
import com.example.dao.model.entity.Scrolling;
import com.example.service.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * Author: yin7331
 * Date: 2023/11/3 22:44
 * Describe:
 */
public class RabbitMQUtil {


    RabbitTemplate rabbitTemplate;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void asyncSendMessage(Scrolling message, RabbitTemplate rabbit) {
        executorService.submit(() -> {
            RabbitTemplate rabbitTemplate=rabbit;
            rabbitTemplate.convertAndSend(RabbitMQConfig.SCROLLING_EXCHANGE,
                    RabbitMQConfig.SCROLLING_EXCHANGE, JSONUtil.toJsonStr(message));
        });
    }

    public static void syncSendMessage(Scrolling message, RabbitTemplate rabbit) {
        RabbitTemplate rabbitTemplate=rabbit;
            rabbitTemplate.convertAndSend(RabbitMQConfig.SCROLLING_EXCHANGE,
                    RabbitMQConfig.SCROLLING_EXCHANGE, message);

    }

    public static Connection getConnection()  {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("192.168.197.128");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 通过工程获取连接
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


    //关闭资源
    public static void closeResource(Channel channel , Connection connection) throws Exception {
        if(channel != null){
            channel.close();
        }
        if(connection != null){
            connection.close();
        }
    }


    public static void main(String[] args) throws Exception {
        Connection con = RabbitMQUtil.getConnection();
        System.out.println(con);
        //         amqp://admin@192.168.6.100:5672/
        con.close();
    }

}
