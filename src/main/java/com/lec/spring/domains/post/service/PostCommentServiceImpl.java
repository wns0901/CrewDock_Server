package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostComment;
import com.lec.spring.domains.post.repository.PostCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    private final PostCommentRepository postCommentRepository;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public PostComment saveComment(PostComment postComment) {
        if (postComment.getParentComment() != null) {
            PostComment parentComment = postCommentRepository.findById(postComment.getParentComment().getId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));

            if (parentComment.getParentComment() != null) {
                throw new RuntimeException("대댓글의 대댓글은 작성할 수 없습니다.");
            }
            postComment.setParentComment(parentComment);
        }
        return postCommentRepository.save(postComment);
    }

    @Override
    public Map<String, Object> getCommentsByPostId(Long postId) {
        List<PostComment> comments = postCommentRepository.findCommentsByPostId(postId);
        long count = postCommentRepository.countCommentsByPostId(postId);

        Map<String, Object> result = new HashMap<>();
        result.put("comments", comments);
        result.put("count", count);

        return result;
    }

    @Override
    @Transactional
    public PostComment updateFixedStatus(Long commentId, Boolean isFixed) {
        Boolean fixed = (isFixed == null || isFixed == false) ? true : false;
        postCommentRepository.updateFixedStatus(commentId, fixed);
        return postCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    @Transactional
    public void deleteCommentById(Long postId, Long commentId) {
        PostComment comment = postCommentRepository.findById(commentId)
                .orElse(null);

        if(comment.getParentComment() != null) {
            Long parentsId = comment.getParentComment().getId();
            if (parentsId.equals(commentId)) {
                postCommentRepository.softDeleteParentComment(parentsId, true, "삭제된 댓글입니다.");
            } else {
                postCommentRepository.softDeleteComment(commentId, true);
            }
        } else {
            postCommentRepository.softDeleteComment(commentId, true);
        }
    }

    @Override
    public void deleteAllCommentByPostId(Long postId) {
        postCommentRepository.deleteAllCommentsByPostId(postId);
    }
}
