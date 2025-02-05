package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;

public interface PostCommentService {
    PostComment saveComment(PostComment postComment);

    long getCommentsCount(Long postId);

    PostComment saveChildComment(PostComment postComment, Long parentsId);

    PostComment getCommentById(Long commentId);

    List<PostComment> getCommentsByPostId(Long postId);

    PostComment updateFixedStatus(Long commentId, Boolean isFixed);

    void deleteCommentById(Long parentsId, Long commentId);
}
