package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentCommentRepository extends JpaRepository<RecruitmentComment, Long> {

    List<RecruitmentComment> findByPostAndCommentIsNullOrderByCreatedAtAsc(RecruitmentPost post);
    //
}
