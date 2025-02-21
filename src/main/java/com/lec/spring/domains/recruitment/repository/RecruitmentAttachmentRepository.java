package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.QRecruitmentAttachment;
import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentAttachmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecruitmentAttachmentRepository extends JpaRepository<RecruitmentAttachment, Long>, QRecruitmentAttachmentRepository {
    // 특정 모집글의 모든 첨부파일 조
    List<RecruitmentAttachment> findByPost(RecruitmentPost post);
}

