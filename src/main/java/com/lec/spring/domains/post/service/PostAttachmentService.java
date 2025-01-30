package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PostAttachmentService {
    PostAttachment findById(Long attachmentId);

    PostAttachment uploadPostAttachment(Map<Long, MultipartFile> files, Long PostId);

    List<PostAttachment> getPostAttachmentByPostId(Long postId);

    PostAttachment updatePostAttachment(PostAttachment postAttachment, Long postId);

    PostAttachment deletePostAttachment(Long attachmentId);
}
