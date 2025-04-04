package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.AllPostCommentDTO;
import com.lec.spring.domains.post.dto.PostCommentDTO;
import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;
import java.util.Map;

public interface PostCommentService {
    PostComment saveComment(PostCommentDTO postComment);

    AllPostCommentDTO getCommentsByPostId(Long postId);

    PostCommentDTO updateFixedStatus(Long commentId, Boolean isFixed);

    String deleteCommentById(Long postId, Long commentId);

    void deleteAllCommentByPostId(Long postId);
}
