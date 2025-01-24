package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecruitmentPostService {

    // 모집글 목록 조회 (페이지네이션) // 메인페이지
    List<RecruitmentPost> RecruitmentPostlist(Integer page, Model model);

    // 특정 모집글 상세 조회 // 메인페이지 상세 조회
    RecruitmentPost detailRecruitmentPost (Long id);

    // 모집글 적기
    RecruitmentPost writeRecruitmentPost (RecruitmentPost recruitmentPost, MultipartFile file);

    // 모집글 수정
    RecruitmentPost updateRecruitmentPost (RecruitmentPost recruitmentPost, MultipartFile file);

    // 모집글 삭제
    int delete(Long id);

    // 검색과 조회는 어떻게 해야할까

}
