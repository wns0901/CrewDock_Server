package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecruitmentAttachmentRepository extends JpaRepository<RecruitmentAttachment, Long> {
    // 특정 모집글의 모든 첨부파일 조회
    List<RecruitmentAttachment> findAllByPostId(Long postId);
}

