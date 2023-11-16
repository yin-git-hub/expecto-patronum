package com.example;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RandomInsertGenerator {

    public static void main(String[] args) {

        LinkedList<Integer> integers = null;
        for (Integer integer : integers) {
            System.out.println(integer);
        }

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
