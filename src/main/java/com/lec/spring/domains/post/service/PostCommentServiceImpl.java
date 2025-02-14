package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostCommentDTO;
import com.lec.spring.domains.post.entity.PostComment;
import com.lec.spring.domains.post.repository.PostCommentRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public PostCommentDTO saveComment(PostComment postComment) {
        if (postComment.getParentComment() != null && postComment.getParentComment().getId() != null) {
            PostComment parentComment = postCommentRepository.findById(postComment.getParentComment().getId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));

            postComment.setParentComment(parentComment);
        } else {
            postComment.setParentComment(null);
        }

        PostComment savedComment = postCommentRepository.save(postComment);

        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setId(savedComment.getId());
        commentDTO.setFixed(savedComment.getFixed());
        commentDTO.setDeleted(savedComment.getDeleted());
        commentDTO.setParentsId(savedComment.getParentComment() != null ? savedComment.getParentComment().getId() : null);
        commentDTO.setContent(savedComment.getContent());
        commentDTO.setPostId(savedComment.getPostId());
        commentDTO.setUserId(savedComment.getUser().getId());
        commentDTO.setUserNickname(savedComment.getUser().getNickname());
        commentDTO.setCreatedAt(savedComment.getCreatedAt());

        return commentDTO;
    }

    @Override
    public Map<String, Object> getCommentsByPostId(Long postId) {
        List<PostComment> commentList = postCommentRepository.findCommentsByPostId(postId);
        long count = postCommentRepository.countCommentsByPostId(postId);

        List<PostCommentDTO> commentsList = new ArrayList<>();

        for (PostComment comment : commentList) {
            PostCommentDTO commentDTO = new PostCommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setPostId(comment.getPostId());
            commentDTO.setUserId(comment.getUser().getId());
            commentDTO.setUserNickname(comment.getUser().getNickname());
            commentDTO.setContent(comment.getContent());
            commentDTO.setDeleted(comment.getDeleted());
            commentDTO.setFixed(comment.getFixed() != null ? comment.getFixed() : false);
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setParentsId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);

            commentsList.add(commentDTO);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("comments", commentsList);
        result.put("count", count);

        return result;
    }

    @Override
    @Transactional
    public PostCommentDTO updateFixedStatus(Long commentId, Boolean isFixed) {
        PostComment fixedComment =  postCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        fixedComment.setFixed(isFixed);
        postCommentRepository.save(fixedComment);

        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setId(fixedComment.getId());
        commentDTO.setFixed(fixedComment.getFixed());
        commentDTO.setDeleted(fixedComment.getDeleted());
        commentDTO.setParentComment(fixedComment.getParentComment());
        commentDTO.setContent(fixedComment.getContent());
        commentDTO.setPostId(fixedComment.getPostId());
        commentDTO.setUserId(fixedComment.getUser().getId());
        commentDTO.setUserNickname(fixedComment.getUser().getNickname());
        commentDTO.setCreatedAt(fixedComment.getCreatedAt());

        return commentDTO;
    }

    @Override
    @Transactional
    public void deleteCommentById(Long postId, Long commentId) {
        PostComment comment = postCommentRepository.findById(commentId)
                .orElse(null);

        List<PostComment> childComments = postCommentRepository.findByParentCommentId(commentId);
        for (PostComment childComment : childComments) {
            postCommentRepository.softDeleteComment(childComment.getId(), true);
        }

        if (comment.getParentComment() != null) {
            postCommentRepository.softDeleteParentComment(commentId, true, "삭제된 댓글입니다.");
        } else {
            postCommentRepository.softDeleteComment(commentId, true);
        }
    }

    @Override
    public void deleteAllCommentByPostId(Long postId) {
        postCommentRepository.deleteAllCommentsByPostId(postId);
    }
}
