package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;

import java.util.List;

public class RecruitmentCommentServiceImpl implements RecruitmentCommentService {

    @Override
    public List<RecruitmentComment> commentList() {
        // 그냥 출력하면 안되는건가...
        return List.of();
    }

    @Override
    public RecruitmentComment createRecruitmentComment(Long id) {
        // 검증할 것
        // 작성 유무, 로그인 유무, 부모 유무

        // 자식 리스트가 없고 부모가 없다면 부모
        // 부모가 있다면 자식 리스트에 넣어라.

        RecruitmentComment createdComment = new RecruitmentComment();

        return createdComment;
    }

    @Override
    public int deleteRecruitmentPost(Long id) {
        return 0;
    }
}
