package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.dto.RecruitmentPostCommentsDTO;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentPostDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitmentPostService {

    // 모집글 전체 조회 (페이징 적용)
    Page<RecruitmentPostDTO> findAll(int page);

    // 필터 옵션 모집글 조회(페이징)
    Page<RecruitmentPostDTO> findByFilters(String stack, String position, String proceedMethod, String region, Pageable pageable);

    // 마감 기한 3일전
    Page<RecruitmentPostDTO> findClosingRecruitments(int page);

    // 캡틴이 만든 모집글 목록 조회
    List<RecruitmentPost> myRecruitmentPost(Long userId);

    // 특정 모집글 상세 조회
    RecruitmentPostDTO detailRecruitmentPost(Long id);

    // 모집글 등록
    RecruitmentPost writeRecruitmentPost(RecruitmentPost recruitmentPost);

    // 모집글 수정
    RecruitmentPost updateRecruitmentPost(Long id, RecruitmentPost recruitmentPost);

    // 모집글 삭제
    void deleteRecruitmentPost(Long id);

    // 특정 유저의 모집글 조회 (전체)
    List<RecruitmentPostCommentsDTO> getUserRecruitmentPosts(Long userId);

    // 특정 유저의 모집글 조회 (row 개수 제한)
    List<RecruitmentPostCommentsDTO> getUserRecruitmentPostsWithLimit(Long userId, int row);

}
