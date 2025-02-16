package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.post.dto.PostAttachmentDTO;
import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.recruitment.dto.RecruitmentAttachmentDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentAttachmentRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.service.RecruitmentAttachmentService;
import com.lec.spring.global.common.util.BucketDirectory;
import com.lec.spring.global.common.util.s3.S3Service;
import com.lec.spring.global.common.util.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentAttachmentServiceImpl implements RecruitmentAttachmentService {

    private final RecruitmentAttachmentRepository attachmentRepository;
    private final RecruitmentPostRepository postRepository;
    private final S3ServiceImpl s3ServiceImpl; // 기존 S3Service 활용

    // 특정 모집글의 모든 첨부파일 조회
    @Override
    public List<RecruitmentAttachment> findAllByPostId(Long postId) {
        return attachmentRepository.findAllByPostId(postId);
    }

    @Override
    public RecruitmentAttachment findById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("첨부파일을 찾을 수 없습니다."));
    }

    // 특정 모집글에 첨부파일 추가 (S3 업로드 방식)
    @Override
    public List<RecruitmentAttachment> saveAttachment(List<MultipartFile> files, Long recruitmentId) {
        List<RecruitmentAttachment> postAttachments = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileUrl = s3ServiceImpl.uploadFile(file, BucketDirectory.POST);
            String fileName = s3ServiceImpl.getFileName(fileUrl);

            RecruitmentAttachmentDTO recruitmentAttachmentDTO = new RecruitmentAttachmentDTO();
            recruitmentAttachmentDTO.setId(recruitmentId);
            recruitmentAttachmentDTO.setUrl(fileUrl);
            recruitmentAttachmentDTO.setFileName(fileName);

            RecruitmentAttachment recruitmentAttachment = RecruitmentAttachment.builder()
                    .id(recruitmentAttachmentDTO.getId())
                    .url(recruitmentAttachmentDTO.getUrl())
                    .build();

            postAttachments.add(recruitmentAttachment);
        }

        return attachmentRepository.saveAll(postAttachments);
    }

    // 특정 첨부파일 삭제 (S3에서도 삭제)
    @Override
    public void delete(Long id) {
        RecruitmentAttachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("첨부파일을 찾을 수 없습니다."));

        // S3에서도 삭제
        s3ServiceImpl.deleteFile(attachment.getUrl());

        attachmentRepository.delete(attachment);
    }
}
