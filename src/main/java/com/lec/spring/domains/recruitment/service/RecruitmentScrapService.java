package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;

import java.util.List;

public interface RecruitmentScrapService {

    // 모집글 스크랩
    void scrapPost(Long postId, Long userId);

    // 모집글 스크랩 취소
    void unScrapPost(Long postId, Long userId);

    // 내가 스크랩한 모집글 목록 조회
    List<RecruitmentScrap> getScrappedPosts(Long userId);

    List<RecruitmentScrap> getScrappedPostsWithLimit(Long userId, int row);
}
