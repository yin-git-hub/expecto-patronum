package com.example;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.hutool.socket.SocketUtil;
import com.example.dao.model.entity.Scrolling;
import com.example.service.impl.websocketImpl.ScrollingWebsocketServiceImpl;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RandomInsertGenerator {
    public static void main(String[] args) {
        LinkedList<Integer> integers = new LinkedList<>();
        integers.add(1);
        HashMap<Integer, List> i = new HashMap<>();
        i.put(1, integers);
        i.remove(1);
        List<Integer> currentList = i.get(1);
        System.out.println("currentList = " + currentList);
    }
    /**
     * 生成sql insergt 语句
     */
    private static void sqlInsert() {
        int numberOfInserts = 100; // 要生成的插入语句数量
        for (int i = 0; i < numberOfInserts; i++) {
            String insertStatement = generateRandomInsertStatement();
            System.out.println(insertStatement);
        }
    }

    public static String generateRandomInsertStatement() {
        Random random = new Random();

        int file_md5 = random.nextInt(10000); // 生成随机整数值
        int file_name = random.nextInt(10000);
        int bucket_name = random.nextInt(10000);
        int object_key = random.nextInt(10000);
        int total_size = random.nextInt(10000);

        String insertStatement = "INSERT INTO video_info (file_md5, file_name, bucket_name, object_key, total_size) " +
                "VALUES (" + file_md5 + ", " + file_name + ", " + bucket_name + ", " + object_key + ", " + total_size + ");";

        return insertStatement;
    }
}
