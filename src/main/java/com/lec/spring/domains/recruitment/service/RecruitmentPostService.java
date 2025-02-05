package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitmentPostService {

    // 모집글 전체 조회 (페이징 적용)
    Page<RecruitmentPost> findAll(Pageable pageable);

    // 유저가 만든 모집글 목록 조회
    List<RecruitmentPost> myRecruitmentPost(Long userId);

    // 특정 모집글 상세 조회
    RecruitmentPost detailRecruitmentPost(Long id);

    // 모집글 등록
    RecruitmentPost writeRecruitmentPost(RecruitmentPost recruitmentPost);

    // 모집글 수정
    RecruitmentPost updateRecruitmentPost(Long id, RecruitmentPost recruitmentPost);

    // 모집글 삭제
    void deleteRecruitmentPost(Long id);
}
