package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;

public interface PostCommentService {
    PostComment saveComment(PostComment postComment);

    PostComment getCommentById(Long id);

    List<PostComment> getCommentsByPostId(Long postId);

    PostComment updateFixedStatus(Long id, Boolean isFixed);

    PostComment updateDeletedStatus(Long id, Boolean isDeleted);
}
