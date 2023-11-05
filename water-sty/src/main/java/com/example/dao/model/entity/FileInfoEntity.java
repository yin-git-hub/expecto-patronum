package com.example.dao.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: yin7331
 * Date: 2023/11/1 19:04
 * Describe:
 */
@Data
public class FileInfoEntity implements Serializable {
    String id;

    String fileMd5;

    String fileName;

    String bucketName;

    String objectKey;

    Long totalSize;

    Integer area;

    Date updateDate;
    private static final long serialVersionUID = 1L;

}
