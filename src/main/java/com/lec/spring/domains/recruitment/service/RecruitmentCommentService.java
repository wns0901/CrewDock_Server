package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.user.entity.User;

import java.util.List;

public interface RecruitmentCommentService {

        // ✅ 모집글에 속한 전체 댓글 & 대댓글 조회
        List<RecruitmentComment> findCommentList(Long postId);

        // ✅ 특정 댓글 조회 (대댓글 조회를 위해 필요)
        RecruitmentComment getCommentById(Long commentId);

        // ✅ 댓글 작성 (부모 댓글 ID가 있으면 대댓글)
        RecruitmentComment createRecruitmentComment(Long postId, Long userId, String content, Long parentCommentId);

        // ✅ 모집글 내 댓글 개수 조회
        int countRecruitmentComment(Long postId);

        // ✅ 댓글 삭제 (대댓글이 있으면 "삭제된 댓글입니다." 처리)
        int deleteRecruitmentComment(Long commentId);
}

