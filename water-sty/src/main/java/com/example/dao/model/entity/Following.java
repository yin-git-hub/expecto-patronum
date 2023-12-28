package com.example.dao.model.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Following {
    private Long id;

    private Long userId;

    private Long upId;

    private Long groupId;

    private List<Long> groupIds;

    private Date createDate;

    private Date updateDate;
}
