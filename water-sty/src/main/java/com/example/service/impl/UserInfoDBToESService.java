package com.example.service.impl;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.Message;
import com.example.dao.model.entity.UserInfoES;
import com.example.service.UserInfoESService;
import com.example.service.utils.CanalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/20 20:10
 * Describe:
 */
@Component
public class UserInfoDBToESService {

    @Autowired
    UserInfoESService userInfoESService;

    RowChange rowChage = null;
    @Scheduled(fixedRate=10000)
    private void printEntry() {
        List<CanalEntry.Entry> entrys=null;
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
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                entry.getHeader().getTableName();
                if(entry.getHeader().getSchemaName().equals("expecto_patronum")
                        &&entry.getHeader().getTableName().equals("user_info")
                ){
                    if (eventType == CanalEntry.EventType.DELETE) {
                        printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        printColumn(rowData.getAfterColumnsList());
                    } else {
                        printColumn(rowData.getAfterColumnsList());
                    }
                }
            }
        }
    }

    private void printColumn(List<CanalEntry.Column> columns) {
         UserInfoES userInfoES = new UserInfoES();
        for (CanalEntry.Column column : columns) {
            if (column.getName().equals("id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                userInfoES.setId(Long.valueOf(value));

            } else if (column.getName().equals("user_id")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                userInfoES.setUserId(Long.valueOf(value));

            } else if (column.getName().equals("nickname")) {
                String value = column.getValue();
                if (value == null || value == "") {
                    value = "00000";
                }
                userInfoES.setNickname(value);
            }
        }
        userInfoESService.save(userInfoES);
    }


}
