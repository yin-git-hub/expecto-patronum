package com.example.service;

import com.example.dao.model.entity.UserInfoES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: yin7331
 * Date: 2023/11/20 20:10
 * Describe:
 */
@Repository
public interface UserInfoESService extends ElasticsearchRepository<UserInfoES, String> {

    Page<UserInfoES> findByNickname(String content, Pageable pageable);

    Page<UserInfoES> findByNicknameLike(String content, Pageable pageable);
}
