package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentCommentRepository extends JpaRepository<RecruitmentComment, Long> {
}
