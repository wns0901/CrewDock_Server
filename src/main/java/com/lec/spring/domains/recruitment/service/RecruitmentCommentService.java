package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.user.entity.User;

import java.util.List;

public interface RecruitmentCommentService {
    // 특정 모집글의 댓글 목록 조회
    List<RecruitmentComment> commentList(RecruitmentPost id);

    // 모집글의 댓글 추가
    RecruitmentComment createRecruitmentComment (Long id, User userId, RecruitmentComment recruitmentComment);

    // 모집글의 대댓글 추가
//    RecruitmentComment childRecruitmentComment(Long recruitmentId, Long commentId);

    // 삭제하기
    int deleteRecruitmentPost (Long id);
}
