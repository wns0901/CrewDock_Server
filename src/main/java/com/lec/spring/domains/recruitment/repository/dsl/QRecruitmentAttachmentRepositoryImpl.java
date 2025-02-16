package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.post.entity.QPostAttachment;
import com.lec.spring.domains.recruitment.entity.QRecruitmentAttachment;
import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class QRecruitmentAttachmentRepositoryImpl implements QRecruitmentAttachmentRepository {
    private final JPAQueryFactory queryFactory;
    private final QRecruitmentAttachment qRecruitmentAttachment = QRecruitmentAttachment.recruitmentAttachment;

    public QRecruitmentAttachmentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RecruitmentAttachment> findByRecruitmentId(Long recruitmentId) {
        return queryFactory
                .selectFrom(qRecruitmentAttachment)
                .where(qRecruitmentAttachment.post.id.eq(recruitmentId))
                .fetch();
    }
}
