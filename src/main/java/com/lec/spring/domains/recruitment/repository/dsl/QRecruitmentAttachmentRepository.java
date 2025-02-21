package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;

import java.util.List;

public interface QRecruitmentAttachmentRepository {
    List<RecruitmentAttachment> findByRecruitmentId(Long recruitmentId);
}
