package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentAttachmentRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecruitmentAttachmentServiceImpl implements RecruitmentAttachmentService {

    private final RecruitmentAttachmentRepository attachmentRepository;
    private final RecruitmentPostRepository postRepository;

    // 특정 모집글의 모든 첨부파일 조회
    @Override
    public List<RecruitmentAttachment> findAllByPostId(Long postId) {
        return attachmentRepository.findAllByPostId(postId);
    }

    // 특정 첨부파일 조회
    @Override
    public RecruitmentAttachment findById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("첨부파일을 찾을 수 없습니다."));
    }

    // 특정 모집글에 첨부파일 추가
    @Override
    public RecruitmentAttachment save(Long postId, MultipartFile file) {
        RecruitmentPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));

        // 파일 저장 (예제 코드, 실제 파일 저장 로직 필요)
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String fileUrl = "/uploads/" + fileName; // 예제 URL, 실제 저장 경로로 변경 필요

        RecruitmentAttachment attachment = RecruitmentAttachment.builder()
                .post(post)
                .url(fileUrl)
                .build();

        return attachmentRepository.save(attachment);
    }

    // 특정 첨부파일 삭제
    @Override
    public void delete(Long id) {
        RecruitmentAttachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("첨부파일을 찾을 수 없습니다."));
        attachmentRepository.delete(attachment);
    }
}
