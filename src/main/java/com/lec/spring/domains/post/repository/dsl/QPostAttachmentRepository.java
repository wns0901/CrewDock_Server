package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.entity.PostAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QPostAttachmentRepository {
    PostAttachment findByAttachmentId(Long attachmentId);

    List<PostAttachment> findByPostId(Long postId);

    void deleteByAttachmentId(Long attachmentId);
}
