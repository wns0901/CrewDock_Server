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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPosts(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {

        List<PostDTO> posts;

        if(row>0){
            posts = postService.getUserPostWithLimit(userId, row);
        } else{
            posts = postService.getUserPost(userId);
        }

        return ResponseEntity.ok(posts);


    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPosts(
            @ModelAttribute PostDTO postDTO,
            @PageableDefault(page = 1, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getPosts(postDTO, pageable));
    }

    @GetMapping("/{postId}")
    public PostDTO getPostDetail(@PathVariable Long postId) {
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
