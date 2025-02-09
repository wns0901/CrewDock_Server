package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.post.entity.QPostAttachment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class QPostAttachmentRepositoryImpl implements QPostAttachmentRepository {
    private final JPAQueryFactory queryFactory;
    private final QPostAttachment qPostAttachment = QPostAttachment.postAttachment;

    public QPostAttachmentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public PostAttachment findByAttachmentId(Long attachmentId) {
        return queryFactory
                .selectFrom(qPostAttachment)
                .where(qPostAttachment.id.eq(attachmentId))
                .fetchOne();
    }

    @Override
    public List<PostAttachment> findByPostId(Long postId) {
        return queryFactory
                .selectFrom(qPostAttachment)
                .where(qPostAttachment.postId.eq(postId))
                .fetch();
    }

    @Override
    public void deleteByAttachmentId(Long attachmentId) {
        queryFactory
            .delete(qPostAttachment)
            .where(qPostAttachment.id.eq(attachmentId))
            .execute();
    }
}
