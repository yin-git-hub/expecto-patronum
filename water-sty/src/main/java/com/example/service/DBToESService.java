package com.example.service;


import com.alibaba.otter.canal.protocol.CanalEntry;
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
    void deleteColumn(List<CanalEntry.Column> columns);
}
