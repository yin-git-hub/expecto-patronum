package com.example.service;

import com.example.dao.model.entity.Comment;

/**
 * Author: yin7331
 * Date: 2024/1/7 21:41
 * Describe:
 */
public interface CommentService {
    Long addComment(Comment comment);

    void deleteComment(Long commentId);
}
