package com.example.service.impl;


import com.alibaba.otter.canal.protocol.CanalEntry;
import com.example.dao.model.entity.UserInfoES;
import com.example.service.DBToESService;
import com.example.service.UserInfoESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/20 20:10
 * Describe:
 */
@Component
public class UserInfoDBToESService  implements DBToESService {

    @Autowired
    UserInfoESService userInfoESService;
    @Override
  public void printColumn(List<CanalEntry.Column> columns) {
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

    @Override
    public void deleteColumn(List<CanalEntry.Column> columns) {

    }


}
