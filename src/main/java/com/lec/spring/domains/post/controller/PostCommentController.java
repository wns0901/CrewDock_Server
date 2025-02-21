package com.lec.spring.domains.post.controller;

import com.lec.spring.domains.post.dto.AllPostCommentDTO;
import com.lec.spring.domains.post.dto.PostCommentDTO;
import com.lec.spring.domains.post.entity.PostComment;
import com.lec.spring.domains.post.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostCommentController {
    private final PostCommentService postCommentService;

    @GetMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public ResponseEntity<AllPostCommentDTO> getPostCommentsWithCount(@PathVariable Long postId) {
        AllPostCommentDTO result = postCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(result);
    }

    @PostMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public PostComment createPostComment(@PathVariable Long postId, @RequestBody PostCommentDTO postComment) {
        postComment.setPostId(postId);
        return postCommentService.saveComment(postComment);
    }

    @PatchMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public ResponseEntity<PostCommentDTO> updateFixStatus(@RequestBody PostComment postComment) {
        PostCommentDTO updateStatus = postCommentService.updateFixedStatus(postComment.getId(), postComment.getFixed());
        return ResponseEntity.ok(updateStatus);
    }

    @DeleteMapping({"/posts/{postId}/comments/{commentId}",
            "/projects/{projectId}/posts/{postId}/comments/{commentId}"})
    public ResponseEntity<String> deletePostComment(@PathVariable Long postId, @PathVariable Long commentId) {
        String result = postCommentService.deleteCommentById(postId, commentId);
        return ResponseEntity.ok(result);
    }
}
