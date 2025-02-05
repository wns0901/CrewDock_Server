package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.user.entity.User;

import java.util.List;

public interface RecruitmentCommentService {
    // 특정 모집글의 댓글 목록 조회
    List<RecruitmentComment> findCommentList(RecruitmentPost id);

    // 모집글의 댓글 추가
    RecruitmentComment createRecruitmentComment (Long id, RecruitmentComment recruitmentComment, String nickname);

    // 댓글 수 가져오기
    int countRecruitmentComment(Long id);

    // 삭제하기
    int deleteRecruitmentPost (Long id);
}
