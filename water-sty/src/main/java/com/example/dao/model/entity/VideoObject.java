package com.example.dao.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoObject implements Serializable {
    private String bucket;
    private String region;
    private String object;
    private String etag;
    private long size;
    private boolean deleteMarker;
    private Map<String, String> userMetadata;
}
