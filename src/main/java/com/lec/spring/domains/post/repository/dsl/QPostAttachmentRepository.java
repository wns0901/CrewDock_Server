package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.entity.PostAttachment;

import java.util.List;

public interface QPostAttachmentRepository {
    List<PostAttachment> findByPostId(Long postId);
}
