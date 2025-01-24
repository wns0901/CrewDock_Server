package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostComment;

public interface PostCommentService {
    PostComment saveComment(PostComment postComment);

    PostComment getCommentById(Long id);

    PostComment updateFixedStatus(Long id, Boolean isFixed);

    PostComment updateDeletedStatus(Long id, Boolean isDeleted);
}
