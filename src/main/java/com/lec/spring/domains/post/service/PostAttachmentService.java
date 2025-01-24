package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostAttachment;
import org.springframework.web.multipart.MultipartFile;

public interface PostAttachmentService {
    PostAttachment findById(Long id);

    PostAttachment uploadPostAttachment(MultipartFile multipartFile, Long PostId);

    PostAttachment updatePostAttachment(PostAttachment postAttachment, Long postId);

    PostAttachment deletePostAttachment(Long id);
}
