package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.dto.ScrappedPostDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.user.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecruitmentScrapService {

     void addScrap(Long userId, Long recruitmentId);

     void removeScrap(Long userId, Long recruitmentId);

    // 내가 스크랩한 모집글 목록 조회
    List<ScrappedPostDTO> getScrappedPosts(Long userId, int row);


}
