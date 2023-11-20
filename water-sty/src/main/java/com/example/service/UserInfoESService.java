package com.example.service;

import com.example.dao.model.entity.UserInfoES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: yin7331
 * Date: 2023/11/20 20:10
 * Describe:
 */
@Repository
public interface UserInfoESService extends ElasticsearchRepository<UserInfoES, String> {
}
