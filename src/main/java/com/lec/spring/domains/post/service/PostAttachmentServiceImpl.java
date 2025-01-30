package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class PostAttachmentServiceImpl implements PostAttachmentService {
    @Override
    public PostAttachment findById(Long attachmentId) {
        return null;
    }

    @Override
    public PostAttachment uploadPostAttachment(Map<Long, MultipartFile> files, Long PostId) {
        return null;
    }

    @Override
    public List<PostAttachment> getPostAttachmentByPostId(Long postId) {
        return List.of();
    }

    @Override
    public PostAttachment updatePostAttachment(PostAttachment postAttachment, Long postId) {
        return null;
    }

    @Override
    public PostAttachment deletePostAttachment(Long attachmentId) {
        return null;
    }
}
