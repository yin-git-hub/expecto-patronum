package com.example.service;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.Message;
import com.example.dao.model.entity.VideoInfoES;
import com.example.service.VideoInfoESService;
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
public interface DBToESService {
    public void printColumn(List<CanalEntry.Column> columns);
}
