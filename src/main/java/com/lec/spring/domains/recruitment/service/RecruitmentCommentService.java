package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.dto.AllRecruitmentCommentDTO;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentCommentDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;

import java.util.List;

public interface RecruitmentCommentService {

        // 모집글에 속한 전체 댓글 & 대댓글 조회
        AllRecruitmentCommentDTO findCommentList(Long postId);

        // 댓글 작성 (부모 댓글 ID가 있으면 대댓글)
        RecruitmentCommentDTO createRecruitmentComment(Long postId, Long userId, String content, Long parentCommentId);

        // 모집글 내 댓글 개수 조회
        int countRecruitmentComment(Long postId);

        // 댓글 삭제 (대댓글이 있으면 "삭제된 댓글입니다." 처리)
        String deleteRecruitmentComment(Long commentId);
}

