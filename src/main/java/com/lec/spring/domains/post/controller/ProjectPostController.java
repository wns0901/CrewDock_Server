package com.lec.spring.domains.post.controller;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/projects/{projectId}/posts")
@RequiredArgsConstructor
public class ProjectPostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProjectPosts(
            @ModelAttribute PostDTO postDTO,
            @PageableDefault(page = 1, size = 10) Pageable pageable)
    {
        return ResponseEntity.ok(postService.getPosts(postDTO, pageable));
    }

    @GetMapping("/{postId}")
    public Post getProjectPostDetail(@PathVariable("projectId") Long projectId, @PathVariable("postId") Long postId) {
        return postService.getPostDetail(postId);
    }

    @PostMapping
    public ResponseEntity<Post> createProjectPost(@RequestBody PostDTO postDTO) {
        Post createdPost = postService.savePost(postDTO);
        return ResponseEntity.ok(createdPost);
    }

    @PatchMapping
    public ResponseEntity<Post> updateProjectPost(@RequestBody PostDTO postDTO) {
        Post updatedPost = postService.updatePost(postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteProjectPost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
