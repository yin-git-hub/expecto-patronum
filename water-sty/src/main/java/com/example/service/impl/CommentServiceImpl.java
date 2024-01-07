package com.example.service.impl;

import com.example.dao.mapper.CommentMapper;
import com.example.dao.model.entity.Comment;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: yin7331
 * Date: 2024/1/7 21:42
 * Describe:
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public Long addComment(Comment comment) {
        commentMapper.insertSelective(comment);
        return comment.getId();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentMapper.deleteByPrimaryKey(commentId);
    }
}
