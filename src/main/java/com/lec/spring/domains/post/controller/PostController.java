package com.lec.spring.domains.post.controller;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getPosts(
            @ModelAttribute PostDTO postDTO,
            @PageableDefault(page = 1, size = 10) Pageable pageable
    ) {
        Page<PostDTO> postPages = postService.getPosts(postDTO, pageable);
        return ResponseEntity.ok(postPages);
    }

    @GetMapping("/{postId}")
    public Post getPostDetail(@PathVariable Long postId) {
        return postService.getPostDetail(postId);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) {
        Post savedPost = postService.savePost(postDTO);
        return ResponseEntity.ok(savedPost);
    }

    @PatchMapping
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        Post updatedPost = postService.updatePost(post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
