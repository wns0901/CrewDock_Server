package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;
import java.util.Map;

public interface PostCommentService {
    PostComment saveComment(PostComment postComment);

    Map<String, Object> getCommentsByPostId(Long postId);

    PostComment updateFixedStatus(Long commentId, Boolean isFixed);

    void deleteCommentById(Long postId, Long commentId);

    void deleteAllCommentByPostId(Long postId);
}
