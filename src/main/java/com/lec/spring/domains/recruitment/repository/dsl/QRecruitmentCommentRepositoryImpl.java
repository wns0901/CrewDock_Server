package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.QRecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class QRecruitmentCommentRepositoryImpl implements QRecruitmentCommentRepository {
    private final JPAQueryFactory queryFactory;

    public QRecruitmentCommentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    // 구현하려는 것. 부모가 있는지 없는지 확인하려고

    // 부모가 있다면 계단식으로
    //   나 부모댓글
    //      나 자식댓글
    @Override
    public List<RecruitmentComment> Comment(List<RecruitmentComment> comments) {

        QRecruitmentComment comment = QRecruitmentComment.recruitmentComment;
        // 생각없이 코딩침...

//        BooleanExpression condition =
        return List.of();
    }

    private BooleanExpression commentFilter (RecruitmentComment comment) {

        return null;
    }

}
