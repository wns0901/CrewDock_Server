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
    private final UserRepository userRepository;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository, UserRepository userRepository) {
        this.postCommentRepository = postCommentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostCommentDTO saveComment(PostComment postComment) {
        if (postComment.getParentComment() != null) {
            PostComment parentComment = postCommentRepository.findById(postComment.getParentComment().getId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));

            if (parentComment.getParentComment() != null) {
                throw new RuntimeException("대댓글의 대댓글은 작성할 수 없습니다.");
            }
            postComment.setParentComment(parentComment);
        }

        PostComment savedComment = postCommentRepository.save(postComment);

        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setId(savedComment.getId());
        commentDTO.setFixed(savedComment.getFixed());
        commentDTO.setDeleted(savedComment.getDeleted());
        commentDTO.setParentsId(savedComment.getParentComment().getId());
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

        List<Map<String, Object>> commentsList = new ArrayList<>();

        for (PostComment comment : commentList) {
            Map<String, Object> commentData = new HashMap<>();
            commentData.put("createdAt", comment.getCreatedAt());
            commentData.put("id", comment.getId());
            commentData.put("postId", comment.getPostId());
            commentData.put("userId", comment.getUser().getId());
            commentData.put("userNickname", comment.getUser().getNickname());
            commentData.put("parentComment", comment.getParentComment());
            commentData.put("content", comment.getContent());
            commentData.put("deleted", comment.getDeleted());
            commentData.put("fixed", comment.getFixed() != null ? comment.getFixed() : false);


            commentsList.add(commentData);
        }

        Map<String, Object> commentsWrapper = new HashMap<>();
        commentsWrapper.put("comment", commentsList);

        Map<String, Object> result = new HashMap<>();
        result.put("comments", commentsWrapper);
        result.put("count", count);

        return result;
    }

    @Override
    @Transactional
    public PostCommentDTO updateFixedStatus(Long commentId, Boolean isFixed) {
        PostComment fixedComment =  postCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setId(fixedComment.getId());
        commentDTO.setFixed(isFixed);
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
