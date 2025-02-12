package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecruitmentAttachmentService {
    // 특정 모집글의 모든 첨부파일 조회
    List<RecruitmentAttachment> findAllByPostId(Long postId);

    // 특정 첨부파일 조회
    RecruitmentAttachment findById(Long id);

    // 특정 모집글에 첨부파일 추가 (파일 업로드)
    RecruitmentAttachment save(Long postId, MultipartFile file);

    // 특정 첨부파일 삭제
    void delete(Long id);
}
