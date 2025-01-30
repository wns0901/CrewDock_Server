package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;

public class PostCommentServiceImpl implements PostCommentService {
    @Override
    public PostComment saveComment(PostComment postComment) {
        return null;
    }

    @Override
    public PostComment getCommentById(Long id) {
        return null;
    }

    @Override
    public List<PostComment> getCommentsByPostId(Long postId) {
        return List.of();
    }

    @Override
    public PostComment updateFixedStatus(Long id, Boolean isFixed) {
        return null;
    }

    @Override
    public PostComment updateDeletedStatus(Long id, Boolean isDeleted) {
        return null;
    }
}
