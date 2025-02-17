package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentCommentRepository extends JpaRepository<RecruitmentComment, Long> {

    // 특정 모집글의 모든 댓글 조회 (부모 댓글만)
    List<RecruitmentComment> findByPostAndCommentIsNullOrderByCreatedAtAsc(RecruitmentPost post);

    // 특정 모집글의 댓글 개수 조회 (부모 댓글 + 대댓글 포함)
    Long countByPostId(RecruitmentPost post);

    List <RecruitmentComment> findByPostId(Long postId);
}
