package com.example.service.impl;

import com.example.dao.mapper.CommentMapper;
import com.example.dao.mapper.UserMapper;
import com.example.dao.model.entity.Comment;
import com.example.dao.model.entity.UserInfo;
import com.example.service.CommentService;
import com.example.service.UserInfoService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: yin7331
 * Date: 2024/1/7 21:42
 * Describe:
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public Long addComment(Comment comment) {
        commentMapper.insertSelective(comment);
        return comment.getId();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public List getFirstLevelComments(Comment comment) {
        if (comment.getReplyToUserId().equals(-1L)) {
            List<Comment> firstLevelComments = commentMapper.getFirstLevelComments(comment);
            List<Comment> newComments = firstLevelComments.parallelStream()
                    .map((c) -> {
                        c.setUserInfo(userMapper.getUserInfoByUserId(c.getUserId()));
                        return c;
                    })
                    .collect(Collectors.toList());

            return newComments;
        }
        return null;
    }

    @Override
    public void reportComment(Long commentId) {
        commentMapper.reportComment(commentId);
    }
}
