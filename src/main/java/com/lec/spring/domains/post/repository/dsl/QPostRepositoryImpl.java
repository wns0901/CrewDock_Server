package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.entity.QPost;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class QPostRepositoryImpl implements QPostRepository {
    private final JPAQueryFactory queryFactory;
    private final QPost qPost = QPost.post;

    public QPostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Post findByPostId(Long postId) {
        return fetchOneEntity(qPost.id.eq(postId));
    }

    @Override
    @Transactional
    public Post updatePost(Post post) {
        if (post.getId() == null) {
            throw new IllegalArgumentException("Post id cannot be null for update");
        }

        BooleanExpression postCondition = qPost.id.eq(post.getId());
        Post existingPost = fetchOneEntity(postCondition);

        if (existingPost != null) {
           queryFactory
               .update(qPost)
               .set(qPost.title, post.getTitle() != null ? post.getTitle() : existingPost.getTitle())
               .set(qPost.content, post.getContent() != null ? post.getContent() : existingPost.getContent())
               .set(qPost.category, post.getCategory() != null ? post.getCategory() : existingPost.getCategory())
               .set(qPost.direction, post.getDirection() != null ? post.getDirection() : existingPost.getDirection())
               .where(postCondition)
               .execute();

           return fetchOneEntity(postCondition);
        }
        return null;
    }

    @Override
    public List<PostDTO> findByCategory(Category category) {
        BooleanExpression condition = qPost.category.eq(category);
        return buildPostProjections(condition)
                .fetch();
    }

    @Override
    public Post findPostById(Long postId) {
        return queryFactory
                .selectFrom(qPost)
                .leftJoin(qPost.user).fetchJoin()
                .where(qPost.id.eq(postId))
                .where(qPost.project.id.isNull())
                .fetchOne();
    }

    @Override
    public Page<PostDTO> findPosts(PostDTO postDTO, Pageable pageable) {
        BooleanExpression condition;

        if (postDTO.getCategory() == null) postDTO.setCategory(Category.NONE);
        if (postDTO.getDirection() == null) postDTO.setDirection(Direction.NONE);

        if (postDTO.getProjectId() == null) {
            condition = qPost.category.eq(postDTO.getCategory());
        }
        else {
            condition = qPost.direction.eq(postDTO.getDirection())
                    .and(qPost.project.id.eq(postDTO.getProjectId()));
        }

        List<PostDTO> posts = buildPostProjections(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = getTotal(condition);
        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public List<PostDTO> findByDirection(Long projectId, Direction direction) {
        BooleanExpression condition = qPost.direction.eq(direction).and(qPost.project.id.eq(projectId));
        return buildPostProjections(condition)
                .fetch();
    }

    @Override
    public Post findProjectPostById(Long postId, Long projectId) {
        return queryFactory
                .selectFrom(qPost)
                .leftJoin(qPost.user).fetchJoin()
                .leftJoin(qPost.project).fetchJoin()
                .where(qPost.id.eq(postId)
                        .and(qPost.project.id.eq(projectId)))
                .fetchOne();
    }

    @Override
    public void deletePostById(Long postId) {
        queryFactory.delete(qPost)
                .where(qPost.id.eq(postId))
                .execute();
    }

    private Post fetchOneEntity(BooleanExpression condition) {
        return queryFactory
                .selectFrom(qPost)
                .leftJoin(qPost.user).fetchJoin()
                .leftJoin(qPost.project).fetchJoin()
                .where(condition)
                .fetchOne();
    }

    private JPAQuery<PostDTO> buildPostProjections(BooleanExpression condition) {
        return queryFactory
                .select(Projections.fields(PostDTO.class,
                        qPost.id,
                        qPost.user,
                        qPost.project,
                        qPost.category,
                        qPost.direction,
                        qPost.title,
                        qPost.content,
                        qPost.attachments,
                        qPost.comments,
                        qPost.createdAt,
                        qPost.user.id.as("userId"),
                        qPost.project.id.as("projectId")
                ))
                .from(qPost)
                .leftJoin(qPost.user)
                .leftJoin(qPost.project)
                .where(condition);
    }

    private Long getTotal(BooleanExpression condition) {
        return queryFactory
                .select(qPost.count())
                .from(qPost)
                .where(condition)
                .fetchOne();
    }
}
