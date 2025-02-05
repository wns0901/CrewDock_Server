package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.QRecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class QRecruitmentCommentRepositoryImpl implements QRecruitmentCommentRepository {
    private final JPAQueryFactory queryFactory;

    public QRecruitmentCommentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QRecruitmentComment comment = QRecruitmentComment.recruitmentComment;
    QRecruitmentComment parentComment = new QRecruitmentComment("parentComment");

    // 부모 id가 같은 아이들만 출력하기 (부모-자식 댓글 계층 구조 유지)
    @Override
    public List<RecruitmentComment> commentListByRecruitmentPost(RecruitmentPost recruitmentPost) {
        List<RecruitmentComment> commentList = queryFactory
                .select(comment)
                .from(comment)
                .leftJoin(comment.comment, parentComment).fetchJoin() // 부모 댓글과 조인
                .where(
                        comment.post.id.eq(recruitmentPost.getId()) // 특정 게시글의 댓글 조회
                )
                .orderBy(comment.id.asc()) // 댓글 ID 기준 정렬 (오래된 댓글 먼저)
                .fetch();

        // 음 그럼 난 이제 어떻게 출력해야하는거지
        return commentList; // 조회한 댓글 리스트 반환
    }
}
