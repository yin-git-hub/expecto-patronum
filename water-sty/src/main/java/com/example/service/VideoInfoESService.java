package com.example.service;

import com.example.dao.model.entity.UserInfoES;
import com.example.dao.model.entity.VideoInfoES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: yin7331
 * Date: 2023/11/20 20:10
 * Describe:
 */
@Repository
public interface VideoInfoESService extends ElasticsearchRepository<VideoInfoES, String> {
    Page<VideoInfoES> findByVideoName(String content, Pageable pageable);

    Page<VideoInfoES> findByVideoNameLike(String content, Pageable pageable);

}
