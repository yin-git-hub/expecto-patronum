package com.example.service;

import com.example.dao.model.entity.ScrollingES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;



/**
 * Author: yin7331
 * Date: 2023/11/16 9:38
 * Describe:
 */
@Repository
public interface ScrollingESService  extends ElasticsearchRepository<ScrollingES, String> {

    Page<ScrollingES> findByScrollingContext(String scrollingContext, Pageable pageable);

    Page<ScrollingES> findByScrollingContextLike(String content, Pageable pageable);
    // List<ScrollingES> findByscrollingContext(String scrollingContext);
}
