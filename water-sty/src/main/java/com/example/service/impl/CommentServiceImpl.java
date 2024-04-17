package com.example.service.impl;

import com.example.controller.Support.UserSupport;
import com.example.dao.mapper.CommentMapper;
import com.example.dao.mapper.UserMapper;
import com.example.dao.model.entity.Comment;
import com.example.dao.model.entity.UserInfo;
import com.example.service.CommentService;
import com.example.service.UserInfoService;
import com.example.service.UserService;
import com.example.service.common.ErrorCode;
import com.example.service.exception.ThrowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserSupport userSupport;


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
        Long currentUserId = userSupport.getCurrentUserId();
        String commentReportKey = "comment-report-"+currentUserId+"-"+commentId;
        String o = (String)redisTemplate.opsForValue().get(commentReportKey);
        ThrowUtils.throwIf(o!=null, ErrorCode.OPERATION_ERROR,"请不要重复操作！");
        redisTemplate.opsForValue()
                .set(commentReportKey
                        ,"1"
                        ,60
                        , TimeUnit.SECONDS);
        commentMapper.reportComment(commentId);
    }
}
