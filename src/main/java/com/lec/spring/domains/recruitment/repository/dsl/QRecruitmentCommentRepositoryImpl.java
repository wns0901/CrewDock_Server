package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.QRecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qrecruitmentCommentRepository") // ✅ 정확한 Bean 이름 추가!
@RequiredArgsConstructor
public class QRecruitmentCommentRepositoryImpl implements QRecruitmentCommentRepository {

    private final JPAQueryFactory queryFactory;

    QRecruitmentComment comment = QRecruitmentComment.recruitmentComment;
    QRecruitmentComment parentComment = new QRecruitmentComment("parentComment");

    // ✅ 모집글에 속한 모든 댓글 조회 (부모-자식 댓글 계층 구조 유지)
    @Override
    public List<RecruitmentComment> commentListByRecruitmentPost(RecruitmentPost recruitmentPost) {
        return queryFactory
                .select(comment)
                .from(comment)
                .leftJoin(comment.comment, parentComment).fetchJoin() // 부모 댓글과 조인
                .where(comment.post.id.eq(recruitmentPost.getId())) // 특정 모집글의 댓글 조회
                .orderBy(comment.comment.id.asc().nullsFirst(), comment.id.asc()) // 부모-자식 정렬 유지
                .fetch();
    }

    // ✅ 특정 댓글의 대댓글 조회 (QueryDSL 적용)
    @Override
    public List<RecruitmentComment> findRepliesByParentComment(RecruitmentComment parentComment) {
        return queryFactory
                .select(comment)
                .from(comment)
                .where(comment.comment.eq(parentComment)) // 특정 부모 댓글에 속한 대댓글만 조회
                .orderBy(comment.createdAt.asc()) // 작성일 기준 정렬
                .fetch();
    }
}


