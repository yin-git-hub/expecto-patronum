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

import java.util.HashMap;
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
    private HashMap<String, DBToESService> dbToESServiceHashMap =
            new HashMap<>();



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
        dbToESServiceHashMap.put("scrolling", scrollingDBToESService);
        dbToESServiceHashMap.put("user_info", userInfoDBToESService);
        dbToESServiceHashMap.put("video_info", videoInfoDBToESService);
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
            String tableName = entry.getHeader().getTableName();
            DBToESService dbToESService = dbToESServiceHashMap.get(tableName);
            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    dbToESService.printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    dbToESService.printColumn(rowData.getAfterColumnsList());
                } else {
                    dbToESService.printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }
}