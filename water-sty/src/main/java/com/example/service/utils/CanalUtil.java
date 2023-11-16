package com.example.service.utils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;

/**
 * Author: yin7331
 * Date: 2023/11/16 10:04
 * Describe:
 */
public class CanalUtil {
    public static CanalConnector connector;

    public static CanalConnector getConnect() {
        // 创建链接
        connector
                = CanalConnectors.newSingleConnector(
                new InetSocketAddress(AddressUtils.getHostIp(), 11111),
                "example",
                "",
                "");

        return connector;
    }

    public static void closeConnector() {
        connector.disconnect();
    }
}
