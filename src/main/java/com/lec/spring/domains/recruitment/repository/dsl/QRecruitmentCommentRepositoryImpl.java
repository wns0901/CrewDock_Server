package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;

import java.util.List;

public class QRecruitmentCommentRepositoryImpl implements QRecruitmentCommentRepository {

    // 부모가 있다면 공간을 조금 빼서

    @Override
    public List<RecruitmentComment> Comment(List<RecruitmentComment> comments) {
        return List.of();
    }


}
