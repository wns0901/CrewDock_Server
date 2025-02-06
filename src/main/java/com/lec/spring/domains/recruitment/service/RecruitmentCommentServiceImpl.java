package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentCommentRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentCommentRepositoryImpl;
import com.lec.spring.domains.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentCommentServiceImpl implements RecruitmentCommentService {

    private final RecruitmentCommentRepository recruitmentCommentRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final QRecruitmentCommentRepositoryImpl recruitmentCommentRepositoryImpl;

    public RecruitmentCommentServiceImpl(RecruitmentCommentRepository recruitmentCommentRepository, RecruitmentPostRepository recruitmentPostRepository, QRecruitmentCommentRepositoryImpl recruitmentCommentRepositoryImpl) {
        this.recruitmentCommentRepository = recruitmentCommentRepository;
        this.recruitmentPostRepository = recruitmentPostRepository;
        this.recruitmentCommentRepositoryImpl = recruitmentCommentRepositoryImpl;
    }

    @Override
    public List<RecruitmentComment> findCommentList(RecruitmentPost id) {
        // 특정 포스트 안에 있는 코멘트 출력
        RecruitmentPost post = recruitmentPostRepository.findById(id.getId()).orElseThrow(IllegalArgumentException::new);
        List<RecruitmentComment> commentList = recruitmentCommentRepositoryImpl.commentListByRecruitmentPost(post);
        return commentList;
    }

    @Override
    public RecruitmentComment createRecruitmentComment(Long id, RecruitmentComment recruitmentComment, String nickname) {

        RecruitmentComment createdComment = new RecruitmentComment();
        // 부모 확인
        RecruitmentComment parentComment = recruitmentComment.getComment();
        return createdComment;
    }

    @Override
    public int countRecruitmentComment(Long id) {
        return 0;
    }

    @Override
    public int deleteRecruitmentPost(Long id) {
        recruitmentCommentRepository.deleteById(id);
        return 1;
    }
}
