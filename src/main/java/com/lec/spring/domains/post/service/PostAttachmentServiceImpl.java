package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostAttachmentDTO;
import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.post.repository.PostAttachmentRepository;
import com.lec.spring.global.common.util.BucketDirectory;
import com.lec.spring.global.common.util.s3.S3ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {
    private final PostAttachmentRepository postAttachmentRepository;
    private final S3ServiceImpl s3ServiceImpl;

    public PostAttachmentServiceImpl(PostAttachmentRepository postAttachmentRepository, S3ServiceImpl s3ServiceImpl) {
        this.postAttachmentRepository = postAttachmentRepository;
        this.s3ServiceImpl = s3ServiceImpl;
    }

    @Override
    public PostAttachment findById(Long attachmentId) {
        return postAttachmentRepository.findByAttachmentId(attachmentId);
    }

    @Override
    public List<PostAttachment> uploadPostAttachment(List<MultipartFile> files, Long postId, Long projectId) {
        List<PostAttachment> postAttachments = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileUrl = s3ServiceImpl.uploadFile(file, BucketDirectory.POST);
            String fileName = s3ServiceImpl.getFileName(fileUrl);

            PostAttachmentDTO postAttachmentDTO = new PostAttachmentDTO();
            postAttachmentDTO.setPostId(postId);
            postAttachmentDTO.setUrl(fileUrl);
            postAttachmentDTO.setFileName(fileName);

            PostAttachment postAttachment = PostAttachment.builder()
                    .postId(postAttachmentDTO.getPostId())
                    .url(postAttachmentDTO.getUrl())
                    .build();

            postAttachments.add(postAttachment);
        }

        return postAttachmentRepository.saveAll(postAttachments);
    }

    @Override
    public List<PostAttachmentDTO> getPostAttachmentByPostId(Long postId) {
        List<PostAttachment> postAttachments = postAttachmentRepository.findByPostId(postId);

        List<PostAttachmentDTO> postAttachmentDTOS = new ArrayList<>();
        for (PostAttachment postAttachment : postAttachments) {
            String fileName = s3ServiceImpl.getFileName(postAttachment.getUrl());
            PostAttachmentDTO postAttachmentDTO = new PostAttachmentDTO();
            postAttachmentDTO.setPostId(postAttachment.getPostId());
            postAttachmentDTO.setUrl(postAttachment.getUrl());
            postAttachmentDTO.setFileName(fileName);
            postAttachmentDTOS.add(postAttachmentDTO);
        }

        return postAttachmentDTOS;
    }

    @Override
    public PostAttachment updatePostAttachment(PostAttachment postAttachment, Long postId) {
        postAttachment.setPostId(postId);
        return postAttachmentRepository.save(postAttachment);
    }

    @Override
    public void deletePostAttachment(Long attachmentId) {
        postAttachmentRepository.deleteById(attachmentId);
    }
}
