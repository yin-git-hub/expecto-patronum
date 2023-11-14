package com.example;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomInsertGenerator {
    public static void main(String[] args) {

        System.out.println(StringUtils.isAnyEmpty(""));
        System.out.println(StringUtils.isAnyEmpty(null));
        System.out.println(StringUtils.isAnyEmpty(" "));
        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.isBlank(null));
        System.out.println(StringUtils.isBlank(" "));
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
