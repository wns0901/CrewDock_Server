package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long>, QRecruitmentPostRepository {
}
