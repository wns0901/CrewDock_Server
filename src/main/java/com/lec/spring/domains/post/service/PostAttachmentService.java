package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostAttachmentDTO;
import com.lec.spring.domains.post.entity.PostAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PostAttachmentService {
    PostAttachment findById(Long attachmentId);

    List<PostAttachment> uploadPostAttachment(List<MultipartFile> files, Long postId, Long projectId);

    List<PostAttachmentDTO> getPostAttachmentByPostId(Long postId);

    PostAttachment updatePostAttachment(PostAttachment postAttachment, Long postId);

    void deletePostAttachment(Long attachmentId);
}
