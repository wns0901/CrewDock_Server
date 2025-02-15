package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentAttachmentRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.service.RecruitmentAttachmentService;
import com.lec.spring.global.common.util.BucketDirectory;
import com.lec.spring.global.common.util.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentAttachmentServiceImpl implements RecruitmentAttachmentService {

    private final RecruitmentAttachmentRepository attachmentRepository;
    private final RecruitmentPostRepository postRepository;
    private final S3Service s3Service; // 기존 S3Service 활용

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
    public RecruitmentAttachment save(Long postId, MultipartFile file) {
        RecruitmentPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));

        // S3에 파일 업로드 후 URL 반환
        String fileUrl = s3Service.uploadFile(file, BucketDirectory.RECRUITMENT);

        if (fileUrl == null || fileUrl.isEmpty()) {
            throw new RuntimeException("S3 업로드 실패: 파일을 저장할 수 없습니다.");
        }

        // DB에 저장
        RecruitmentAttachment attachment = RecruitmentAttachment.builder()
                .post(post)
                .url(fileUrl)
                .build();

        return attachmentRepository.save(attachment);
    }

    // 특정 첨부파일 삭제 (S3에서도 삭제)
    @Override
    public void delete(Long id) {
        RecruitmentAttachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("첨부파일을 찾을 수 없습니다."));

        // S3에서도 삭제
        s3Service.deleteFile(attachment.getUrl());

        attachmentRepository.delete(attachment);
    }
}
