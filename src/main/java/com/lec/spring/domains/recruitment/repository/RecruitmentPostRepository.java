package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;

import java.util.List;

public interface RecruitmentPostRepository {
    // 모집글 조회 (페이징+카테고리)
    List<RecruitmentPost> list();

    // 모집글 적기
    RecruitmentPost write(RecruitmentPost recruitmentPost);

    // 모집글 수정

    // 모집글 삭제
}
