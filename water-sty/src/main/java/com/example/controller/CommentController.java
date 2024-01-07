package com.example.controller;

import com.example.dao.model.entity.Comment;
import com.example.service.CommentService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: yin7331
 * Date: 2024/1/7 21:42
 * Describe:
 */

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    CommentService commentService;

    @PostMapping("/addComment")
    public BaseResponse addComment(@RequestBody Comment comment){
        Long commentId = commentService.addComment(comment);
        return ResultUtils.success(commentId);
    }

    @PostMapping("/deleteComment/{commentId}")
    public BaseResponse deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return ResultUtils.success();
    }
}
