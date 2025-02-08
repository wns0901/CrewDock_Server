package com.lec.spring.domains.post.controller;

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
    public ResponseEntity<Map<String, Object>> getPostCommentsWithCount(@PathVariable Long postId) {
        Map<String, Object> result = postCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(result);
    }

    @PostMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public PostComment createPostComment(@RequestBody PostComment postComment) {
        return postCommentService.saveComment(postComment);
    }

    @PatchMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public ResponseEntity<PostComment> updateFixStatus(@RequestBody PostComment postComment) {
        PostComment updateStatus = postCommentService.updateFixedStatus(postComment.getId(), postComment.getFixed());
        return ResponseEntity.ok(updateStatus);
    }

    @DeleteMapping({"/posts/{postId}/comments/{commentId}",
            "/projects/{projectId}/posts/{postId}/comments/{commentId}"})
    public ResponseEntity<Void> deletePostComment(@PathVariable Long postId, @PathVariable Long commentId) {
        postCommentService.deleteCommentById(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
