package com.example.service.impl;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.example.service.DBToESService;
import com.example.service.utils.CanalUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/20 21:42
 * Describe:
 */
@Component
public class UseDBToESService {
    @Autowired
    ScrollingDBToESService scrollingDBToESService;
    @Autowired
    UserInfoDBToESService userInfoDBToESService;
    @Autowired
    VideoInfoDBToESService videoInfoDBToESService;

    @Scheduled(fixedRate = 1000)
    private void printEntry() {
        List<CanalEntry.Entry> entrys = null;
        CanalConnector connector = CanalUtil.getConnect();
        int batchSize = 10;
        connector.connect();
        connector.rollback();
        Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
        long batchId = message.getId();
        int size = message.getEntries().size();
        if (batchId != -1 || size != 0) {
            entrys = message.getEntries();
        } else {
            return;
        }
        connector.ack(batchId); // 提交确认
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            CanalEntry.EventType eventType = rowChage.getEventType();

            if (!entry.getHeader().getSchemaName().equals("expecto_patronum")
            ) {
                return;
            }
            // todo 享元模式优化此处
            if (entry.getHeader().getTableName().equals("scrolling")) {

                for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == CanalEntry.EventType.DELETE) {
                        scrollingDBToESService.printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        scrollingDBToESService.printColumn(rowData.getAfterColumnsList());
                    } else {
                        scrollingDBToESService.printColumn(rowData.getAfterColumnsList());
                    }
                }
            } else if (entry.getHeader().getTableName().equals("video_info")) {

                for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == CanalEntry.EventType.DELETE) {
                        videoInfoDBToESService.printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        videoInfoDBToESService.printColumn(rowData.getAfterColumnsList());
                    } else {
                        videoInfoDBToESService.printColumn(rowData.getAfterColumnsList());
                    }
                }

            } else if (entry.getHeader().getTableName().equals("user_info")) {

                for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == CanalEntry.EventType.DELETE) {
                        userInfoDBToESService.printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        userInfoDBToESService.printColumn(rowData.getAfterColumnsList());
                    } else {
                        userInfoDBToESService.printColumn(rowData.getAfterColumnsList());
                    }
                }
            }
        }
    }
}
