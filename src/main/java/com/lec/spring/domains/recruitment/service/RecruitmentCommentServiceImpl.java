package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentCommentRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.user.entity.User;

import java.util.List;

public class RecruitmentCommentServiceImpl implements RecruitmentCommentService {

    private final RecruitmentCommentRepository recruitmentCommentRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    public RecruitmentCommentServiceImpl(RecruitmentCommentRepository recruitmentCommentRepository, RecruitmentPostRepository recruitmentPostRepository) {
        this.recruitmentCommentRepository = recruitmentCommentRepository;
        this.recruitmentPostRepository = recruitmentPostRepository;
    }

    @Override
    public List<RecruitmentComment> commentList(RecruitmentPost id) {
        // List<Comment> comments = commentRepository.findByPost(postId);

//        List<RecruitmentComment> comments = recruitmentPostRepository.
        return List.of();
    }

    @Override
    public RecruitmentComment createRecruitmentComment(Long id, User userId, RecruitmentComment recruitmentComment) {
        // 검증할 것
        // 작성 유무, 로그인 유무, 부모 유무
        // 부모 id가 없다면 부모
        // 부모가 있다면 자식 리스트에 넣는다.

        //
        RecruitmentComment createdComment = new RecruitmentComment();
        // 부모 확인
        RecruitmentComment parentComment = recruitmentComment.getComment();

        return createdComment;
    }

    @Override
    public int deleteRecruitmentPost(Long id) {

        recruitmentCommentRepository.deleteById(id);
        return 1;
    }
}
