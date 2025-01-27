package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;

import java.util.List;

public class RecruitmentCommentServiceImpl implements RecruitmentCommentService {
    @Override
    public List<RecruitmentComment> list() {
        return List.of();
    }

    @Override
    public RecruitmentComment writeRecruitmentPost(Long id) {
        return null;
    }

    @Override
    public int deleteRecruitmentPost(Long id) {
        return 0;
    }
}
