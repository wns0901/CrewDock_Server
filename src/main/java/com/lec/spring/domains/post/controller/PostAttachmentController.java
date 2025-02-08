package com.lec.spring.domains.post.controller;

import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.post.service.PostAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostAttachmentController {
    private final PostAttachmentService postAttachmentService;


    @GetMapping({"/posts/{postId}/attachments",
            "/projects/{projectId}/posts/{postId}/attachments"})
    public List<PostAttachment> getPostAttachments(@PathVariable Long postId) {
        return postAttachmentService.getPostAttachmentByPostId(postId);
    }

    @PostMapping({"/posts/{postId}/attatchments",
            "/projects/{projectId}/posts/{postId}/attachments"})
    public PostAttachment registerProjectPostAttachment(@RequestParam("file")MultipartFile file,
                                                        @PathVariable Long postId,
                                                        @PathVariable Long projectId)
    {
        return postAttachmentService.uploadPostAttachment(file, postId, projectId);
    }

    @DeleteMapping({"/posts/{postId}/attachments/{attachmentId}",
            "/projects/{projectId}/posts/{postId}/attachments/{attachmentId}"})
    public void deletePostAttachment(@PathVariable Long attachmentId) {
        postAttachmentService.deletePostAttachment(attachmentId);
    }
}
