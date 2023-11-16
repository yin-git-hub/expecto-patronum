package com.example.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.example.dao.model.entity.ScrollingES;
import com.example.service.ScrollingESService;
import com.example.service.utils.CanalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScrollingDBToESService {

    @Autowired
    ScrollingESService scrollingESService;


    static RowChange rowChage = null;
    @Scheduled(fixedRate=10000)
    private void printEntry() {
        List<Entry> entrys=null;
        CanalConnector connector = CanalUtil.getConnect();
        int batchSize = 10;
        connector.connect();
        connector.rollback();
        Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
        long batchId = message.getId();
        int size = message.getEntries().size();
        if (batchId != -1 || size != 0) {
             entrys = message.getEntries();
        }else {
            return;
        }
        connector.ack(batchId); // 提交确认
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {

                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private void printColumn(List<Column> columns) {
        ScrollingES scrollingES = new ScrollingES();
        for (Column column : columns) {
            if (column.getName().equals("id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                scrollingES.setId(Long.valueOf(value));

            } else if (column.getName().equals("user_id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                scrollingES.setUserId(Long.valueOf(value));

            } else if (column.getName().equals("video_id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                scrollingES.setVideoId(Long.valueOf(value));

            } else if (column.getName().equals("scrolling_context")) {
                scrollingES.setScrollingContext(column.getValue());

            } else if (column.getName().equals("relative_time")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                scrollingES.setRelativeTime(Long.valueOf(value));


            } else if (column.getName().equals("create_date")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(column.getValue());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                scrollingES.setCreateDate(date);
            } else if (column.getName().equals("update_date")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(column.getValue());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                scrollingES.setUpdateDate(date);
            }
        }
        scrollingESService.save(scrollingES);
    }


}
