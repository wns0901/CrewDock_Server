package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.entity.PostComment;
import com.lec.spring.domains.post.entity.QPostComment;
import com.lec.spring.domains.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QPostCommentRepositoryImpl implements QPostCommentRepository {
    private final JPAQueryFactory queryFactory;
    private final  QPostComment qPostComment = QPostComment.postComment;

    public QPostCommentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public long countCommentsByPostId(Long postId) {
        return queryFactory
                .selectFrom(qPostComment)
                .where(qPostComment.postId.eq(postId))
                .fetchCount();
    }

    @Override
    public List<PostComment> findCommentsByPostId(Long postId) {
        return queryFactory
                .select(qPostComment)
                .from(qPostComment)
                .leftJoin(qPostComment.user)
                .leftJoin(qPostComment.parentComment)
                .where(qPostComment.postId.eq(postId))
                .orderBy(qPostComment.id.asc())
                .fetch();
    }

    @Override
    public List<PostComment> findByParentCommentId(Long parentCommentId) {
        return queryFactory
                .selectFrom(qPostComment)
                .where(qPostComment.parentComment.id.eq(parentCommentId))
                .fetch();
    }

    @Override
    public void updateFixedStatus(Long commentId, boolean isFixed) {
        queryFactory
                .update(qPostComment)
                .set(qPostComment.fixed, false)
                .where(qPostComment.fixed.eq(true))
                .execute();

        queryFactory
                .update(qPostComment)
                .set(qPostComment.fixed, isFixed)
                .where(qPostComment.id.eq(commentId))
                .execute();
    }

    @Override
    public void softDeleteParentComment(Long parentsId, boolean isDeleted, String newContent) {
        queryFactory
                .update(qPostComment)
                .set(qPostComment.content, newContent)
                .set(qPostComment.deleted, isDeleted)
                .where(qPostComment.id.eq(parentsId))
                .execute();
    }

    @Override
    public void softDeleteComment(Long commentId, boolean isDeleted) {
        queryFactory
                .update(qPostComment)
                .set(qPostComment.deleted, isDeleted)
                .where(qPostComment.id.eq(commentId))
                .execute();
    }

    @Override
    public void deleteAllCommentsByPostId(Long postId) {
        queryFactory
                .delete(qPostComment)
                .where(qPostComment.parentComment.isNotNull()
                        .and(qPostComment.postId.eq(postId)))
                .execute();

        queryFactory
                .delete(qPostComment)
                .where(qPostComment.parentComment.id.isNull()
                        .and(qPostComment.postId.eq(postId)))
                .execute();
    }
}
