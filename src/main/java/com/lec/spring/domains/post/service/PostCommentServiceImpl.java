package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.AllPostCommentDTO;
import com.lec.spring.domains.post.dto.PostCommentDTO;
import com.lec.spring.domains.post.entity.Post;
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
    public PostCommentDTO saveComment(PostCommentDTO postComment) {

        PostComment parentComment = null;

        if (postComment.getParentCommentId() != null) {
            parentComment = PostComment.builder().id(postComment.getParentCommentId()).build();
        }

        PostComment newComment = PostComment.builder()
                .postId(postComment.getPostId())
                .user(User.builder().id(postComment.getUserId()).build())
                .content(postComment.getContent())
                .parentComment(parentComment)
                .deleted(false)
                .build();

        PostComment savedComment = postCommentRepository.save(newComment);

        return PostCommentDTO.of(savedComment);
    }

    @Override
    public AllPostCommentDTO getCommentsByPostId(Long postId) {
        List<PostComment> commentList = postCommentRepository.findCommentsByPostId(postId);
        long count = postCommentRepository.countCommentsByPostId(postId);

        return AllPostCommentDTO.builder()
                .count(count)
                .comments(commentList.stream().map(PostCommentDTO::of).toList())
                .build();
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
        commentDTO.setUserName(fixedComment.getUser().getNickname());
        commentDTO.setCreatedAt(fixedComment.getCreatedAt());

        return commentDTO;
    }

    @Override
    @Transactional
    public String deleteCommentById(Long postId, Long commentId) {
        PostComment comment = postCommentRepository.findById(commentId)
                .orElse(null);

        List<PostComment> childComments = postCommentRepository.findByParentCommentId(commentId);

        if (!childComments.isEmpty()) {
            comment.setDeleted(true);
            postCommentRepository.save(comment);
            return "parent";
        } else {
            postCommentRepository.delete(comment);
            return "deleted";
        }
    }

    @Override
    public void deleteAllCommentByPostId(Long postId) {
        postCommentRepository.deleteAllCommentsByPostId(postId);
    }
}
