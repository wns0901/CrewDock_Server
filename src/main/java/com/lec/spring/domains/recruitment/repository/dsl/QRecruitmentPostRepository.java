package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface QRecruitmentPostRepository {

    Optional<RecruitmentPost> findByIdWithUserAndProject(Long id);

    Page<RecruitmentPost> findByFilters(String stack, String position, String proceedMethod, String region, Pageable pageable);

    Page<RecruitmentPost> findClosingRecruitments(LocalDate closingDate, Pageable pageable);
}
