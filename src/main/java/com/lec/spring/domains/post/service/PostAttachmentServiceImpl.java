package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.post.repository.PostAttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {
    private final PostAttachmentRepository postAttachmentRepository;

    public PostAttachmentServiceImpl(PostAttachmentRepository postAttachmentRepository) {
        this.postAttachmentRepository = postAttachmentRepository;
    }

    @Override
    public PostAttachment findById(Long attachmentId) {
        return postAttachmentRepository.findByAttachmentId(attachmentId);
    }

    @Override
    public PostAttachment uploadPostAttachment(MultipartFile file, Long postId, Long projectId) {
        String fileUrl = saveFileToStorage(file, postId, projectId);

        PostAttachment postAttachment = PostAttachment.builder()
                .postId(postId)
                .url(fileUrl)
                .build();

        return postAttachmentRepository.save(postAttachment);
    }

    public String saveFileToStorage(MultipartFile file, Long postId, Long projectId) {
        String directoryPath;

        if(projectId != null) {
            directoryPath = String.format("/projects/%d/posts/%d/attachments", projectId, postId);
        } else {
            directoryPath = String.format("/posts/%d/attachments", postId);
        }

        return directoryPath;
    }

    @Override
    public List<PostAttachment> getPostAttachmentByPostId(Long postId) {
        return postAttachmentRepository.findByPostId(postId);
    }

    @Override
    public PostAttachment updatePostAttachment(PostAttachment postAttachment, Long postId) {
        postAttachment.setPostId(postId);
        return postAttachmentRepository.save(postAttachment);
    }

    @Override
    public void deletePostAttachment(Long attachmentId) {
        postAttachmentRepository.deleteByAttachmentId(attachmentId);
    }
}
