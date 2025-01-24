package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;

import java.util.List;

public interface RecruitmentCommentService {
    // 특정 모집글의 댓글 목록 조회
    List<RecruitmentComment> list();

    // 모집글의 댓글 추가
    RecruitmentComment writeRecruitmentPost (Long id);

    // 삭제하기
    int deleteRecruitmentPost (Long id);
}
