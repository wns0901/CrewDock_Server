package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;

import java.util.List;

public interface QRecruitmentCommentRepository {
    //
    List<RecruitmentComment> Comment(List<RecruitmentComment> comments);
}
