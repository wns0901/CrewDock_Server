package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.entity.PostAttachment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class QPostAttachmentRepositoryImpl implements QPostAttachmentRepository {
    private final JPAQueryFactory queryFactory;

    public QPostAttachmentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public PostAttachment findByAttachmentId(Long attachmentId) {
        return queryFactory.selectFrom(QPostAttachment.postAttachment);
    }

    @Override
    public List<PostAttachment> findByPostId(Long postId) {
        return List.of();
    }

    @Override
    public void deleteByAttachmentId(Long attachmentId) {

    }
}
