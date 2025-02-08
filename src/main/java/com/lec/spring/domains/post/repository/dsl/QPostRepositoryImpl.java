package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.dto.ProjectPostDTO;
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

import java.util.List;
import java.util.Optional;

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
    public Page<PostDTO> findByCategoryPage(Category category, Pageable pageable) {
        BooleanExpression condition = qPost.category.eq(category);
        List<PostDTO> posts = buildPostProjections(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = getTotal(condition);
        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public List<ProjectPostDTO> findByDirection(Long projectId, Direction direction) {
        BooleanExpression condition = qPost.direction.eq(direction).and(qPost.project.id.eq(projectId));
        return buildProjectProjections(condition)
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
    public Page<ProjectPostDTO> findByDirectionPage(Long projectId, Direction direction, Pageable pageable) {
        BooleanExpression condition = qPost.direction.eq(direction).and(qPost.project.id.eq(projectId));
        List<ProjectPostDTO> ProjectPosts = buildProjectProjections(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = getTotal(condition);
        return new PageImpl<>(ProjectPosts, pageable, total);
    }

    @Override
    public List<Post> findByUserIdWithrowQuertDSL(Long userId, int row) {
        return queryFactory
                .selectFrom(qPost)
                .leftJoin(qPost.user).fetchJoin()
                .where(qPost.user.id.eq(userId)
                        .and(qPost.project.isNull()))
                .orderBy(qPost.id.desc())
                .limit(row)
                .fetch();
    }

    @Override
    public List<Post> findByUserIdQuertDSL(Long userId) {
        return queryFactory
                .selectFrom(qPost)
                .leftJoin(qPost.user).fetchJoin()
                .where(qPost.user.id.eq(userId)
                        .and(qPost.project.isNull()))  // 프로젝트 게시글 제외
                .orderBy(qPost.id.desc())
                .fetch();
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
                        qPost.user.username.as("userId"),
                        qPost.title,
                        qPost.content,
                        qPost.category,
                        qPost.attachments,
                        qPost.comments,
                        qPost.createdAt
                ))
                .from(qPost)
                .leftJoin(qPost.user).fetchJoin()
                .where(condition);
    }

    private JPAQuery<ProjectPostDTO> buildProjectProjections(BooleanExpression condition) {
        return queryFactory
                .select(Projections.fields(ProjectPostDTO.class,
                        qPost.id,
                        qPost.user.username.as("userId"),
                        qPost.title,
                        qPost.content,
                        qPost.direction,
                        qPost.attachments,
                        qPost.comments,
                        qPost.createdAt
                ))
                .from(qPost)
                .leftJoin(qPost.user).fetchJoin()
                .leftJoin(qPost.project).fetchJoin()
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
