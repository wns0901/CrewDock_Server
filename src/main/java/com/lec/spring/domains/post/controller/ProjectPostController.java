package com.lec.spring.domains.post.controller;

import com.lec.spring.domains.post.dto.ProjectPostDTO;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.service.ProjectPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/posts")
@RequiredArgsConstructor
public class ProjectPostController {
    private final ProjectPostService projectPostService;

    @GetMapping("/")
    public ResponseEntity<Page<ProjectPostDTO>> getProjectPosts(@PathVariable("projectId") Long projectId,
            @RequestParam Direction direction,
            Pageable pageable)
    {
       Page<ProjectPostDTO> projectPostDTOPages = projectPostService.getPostsByDirectionPage(projectId, direction, pageable);
       return ResponseEntity.ok(projectPostDTOPages);
    }

    @GetMapping("/{postId}")
    public Post getProjectPost(@PathVariable("projectId") Long projectId, @PathVariable("postId") Long postId) {
        return projectPostService.getProjectPostDetail(projectId, postId);
    }

    @PostMapping
    public Post createProjectPost(@RequestBody Post post) {
        return projectPostService.savePost(post);
    }

    @PatchMapping
    public Post updateProjectPost(@RequestBody Post post) {
        return projectPostService.updatePost(post);
    }

    @DeleteMapping("/{postId}")
    public void deleteProjectPost(@PathVariable("postId") Long postId) {
        projectPostService.deletePost(postId);
    }
}
