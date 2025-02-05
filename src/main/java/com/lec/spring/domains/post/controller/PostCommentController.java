package com.lec.spring.domains.post.controller;

import com.lec.spring.domains.post.entity.PostComment;
import com.lec.spring.domains.post.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostCommentController {
    private final PostCommentService postCommentService;

    @GetMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public List<PostComment> getPostComments(@PathVariable Long postId) {
        return postCommentService.getCommentsByPostId(postId);
    }

    @PostMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public PostComment createPostComment(@RequestBody PostComment postComment) {
        return postCommentService.saveComment(postComment);
    }

    @PatchMapping({"/posts/{postId}/comments",
            "/projects/{projectId}/posts/{postId}/comments"})
    public PostComment updateFixStatus(@RequestBody PostComment postComment) {
        return postCommentService.updateFixedStatus(postComment.getId(), postComment.getFixed());
    }

    @DeleteMapping({"/posts/{postId}/comments/{commentId}",
            "/projects/{projectId}/posts/{postId}/comments/{commentId}"})
    public void deletePostComment(@PathVariable Long parentsId, @PathVariable Long commentId) {
        postCommentService.deleteCommentById(parentsId, commentId);
    }
}
