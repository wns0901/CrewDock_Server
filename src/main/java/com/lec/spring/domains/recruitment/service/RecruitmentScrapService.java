package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.dto.ScrappedPostDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;

import java.util.List;
import java.util.Map;

public interface RecruitmentScrapService {

    // 모집글 스크랩
    void scrapPost(Long postId, Long userId);

    // 모집글 스크랩 취소
    void unScrapPost(Long postId, Long userId);

    // 내가 스크랩한 모집글 목록 조회
    List<ScrappedPostDTO> getScrappedPosts(Long userId, int row);
}
