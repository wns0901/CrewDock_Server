package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostAttachment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {
    @Override
    public PostAttachment findById(Long id) {
        return null;
    }

    @Override
    public PostAttachment uploadPostAttachment(Map<Long, MultipartFile> files, Long PostId) {
        return null;
    }

    @Override
    public PostAttachment updatePostAttachment(PostAttachment postAttachment, Long postId) {
        return null;
    }

    @Override
    public PostAttachment deletePostAttachment(Long id) {
        return null;
    }
}
