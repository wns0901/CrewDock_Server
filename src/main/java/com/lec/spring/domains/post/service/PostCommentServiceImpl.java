package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostComment;
import com.lec.spring.domains.post.repository.PostCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    private final PostCommentRepository postCommentRepository;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public PostComment saveComment(PostComment postComment) {
        return postCommentRepository.save(postComment);
    }

    @Override
    public long getCommentsCount(Long postId) {
        return postCommentRepository.countCommentsByPostId(postId);
    }

    @Override
    public PostComment saveChildComment(PostComment postComment, Long parentsId) {
        PostComment parentComment = postCommentRepository.findById(parentsId).get();
        postComment.setParentComment(parentComment);

        return postCommentRepository.save(postComment);
    }

    @Override
    public PostComment getCommentById(Long commentId) {
        return postCommentRepository.findByCommentId(commentId);
    }

    @Override
    public List<PostComment> getCommentsByPostId(Long postId) {
        return postCommentRepository.findCommentsByPostId(postId);
    }

    @Override
    public PostComment updateFixedStatus(Long commentId, Boolean isFixed) {
        postCommentRepository.updateFixedStatus(commentId, isFixed);
        return getCommentById(commentId);
    }

    @Override
    public void deleteCommentById(Long parentsId, Long commentId) {
        if(parentsId != null && parentsId == commentId) {
            postCommentRepository.softDeleteParentComment(parentsId, true, "삭제된 댓글입니다.");
        } else {
            postCommentRepository.softDeleteComment(commentId, true);
        }
    }
}
