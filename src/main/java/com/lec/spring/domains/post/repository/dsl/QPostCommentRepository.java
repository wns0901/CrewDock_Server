package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;

public interface QPostCommentRepository {
    long countCommentsByPostId(Long postId);

    List<PostComment> findCommentsByPostId(Long postId);

    List<PostComment> findByParentCommentId(Long parentCommentId);

    void updateFixedStatus(Long commentId, boolean isFixed);

    void softDeleteParentComment(Long parentsId, boolean isDeleted, String newContent);

    void softDeleteComment(Long commentId, boolean isDeleted);

    void deleteAllCommentsByPostId(Long postId);
}
