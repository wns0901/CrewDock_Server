package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitmentPostService {

    // Paging
    Page<RecruitmentPost> findAll(Pageable pageable);

    // 필터옵션

    // Read(상세 글 보여주기)
    RecruitmentPost detailRecruitmentPost(Long id);

    // 모집글 적기
    RecruitmentPost writeRecruitmentPost (RecruitmentPost recruitmentPost);

    // 모집글 수정
    RecruitmentPost updateRecruitmentPost (Long id,RecruitmentPost recruitmentPost);

    // 모집글 삭제
    int deleteRecruitmentPost (Long id);

}
