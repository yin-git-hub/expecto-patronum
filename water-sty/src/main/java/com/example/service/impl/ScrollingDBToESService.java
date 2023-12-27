package com.example.service.impl;

import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.example.dao.model.entity.ScrollingES;
import com.example.service.DBToESService;
import com.example.service.ScrollingESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScrollingDBToESService implements DBToESService {

    @Autowired
    ScrollingESService scrollingESService;
    @Override
    public void printColumn(List<Column> columns) {
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

    @Override
    public void deleteColumn(List<Column> columns) {

    }


}
